package com.example.killerapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.killerapp.models.Country;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditClient extends AppCompatActivity {
    private Integer clientId;
    private final OkHttpClient httpClient = new OkHttpClient();
    public List<Country> countriesList = new ArrayList<>();

    //za deklaracijo polj na layoutu
    EditText tName, tSurname, tRegistrationNumber, tEmail, tPhone, tTaxNumber, tStreetName, tStreetNumber, tPostNumber, tCity;
    Spinner tCountry, tType;
    CheckBox tTaxPayer;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_client);
        Intent intent = getIntent();


        //deklaracija client type spinnerja
        tType = findViewById(R.id.editClientType);
        ArrayAdapter<String> clientTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.client_types));
        clientTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tType.setAdapter(clientTypeAdapter);
        String tempType = intent.getStringExtra("iType");
        switch (tempType){
            case ("Fizična oseba"):
        tType.setSelection(0);
        break;
        case ("Pravna oseba"):
        tType.setSelection(1);
        break;
        case ("Samostojni podjetnik"):
        tType.setSelection(2);
        break;
        case("Drugo"):
        tType.setSelection(3);
        break;
        default:
        tType.setSelection(0);
    }


        //deklaracija polj
        tName = findViewById(R.id.editClientName);
        tSurname = findViewById(R.id.editClientSurname);
        tRegistrationNumber = findViewById(R.id.editClientRegistrationNumber);
        tEmail = findViewById(R.id.editClientEmail);
        tPhone = findViewById(R.id.editClientPhoneNumber);
        tTaxNumber = findViewById(R.id.editClientTaxNumber);
        tStreetName = findViewById(R.id.editClientStreetName);
        tStreetNumber = findViewById(R.id.editClientStreetNumber);
        tName = findViewById(R.id.editClientName);
        tPostNumber = findViewById(R.id.editClientPostNumber);
        tCity = findViewById(R.id.editClientCity);
        tTaxPayer = findViewById(R.id.editTaxCheckBox);
        tCountry = findViewById(R.id.editClientCountry);

        //iz intenta dobimo vrednosti in jih zapišemo v polja

        clientId = intent.getIntExtra("EDIT_CLIENT_ID", 0);

        String[] nameAndSurname = intent.getStringExtra("iTitle").split(" ");
        tName.setText(nameAndSurname[0]);
        tSurname.setText(nameAndSurname[1]);
        tRegistrationNumber.setText(intent.getStringExtra("iRegistrationNumber"));
        tEmail.setText(intent.getStringExtra("iMail"));
        tPhone.setText(intent.getStringExtra("iPhone"));
        tTaxNumber.setText(intent.getStringExtra("iTaxNumber"));
        tStreetName.setText(intent.getStringExtra("iStreetName"));
        tStreetNumber.setText(intent.getStringExtra("iStreetNumber"));
        tPostNumber.setText(intent.getStringExtra("iPostNumber"));
        tCity.setText(intent.getStringExtra("iCity"));
        tTaxPayer.setEnabled(intent.getBooleanExtra("iTaxPayer", false));

        String tempCountryName = intent.getStringExtra("iCountry");
        Integer tempCountryId = intent.getIntExtra("iCountryId", 0);

        //deklaracija country spinnerja
        String url = getString(R.string.countries);
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Country[] countries = new ObjectMapper().readValue(response.body().string(), Country[].class);
            countriesList = Arrays.asList(countries);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayAdapter<Country> dataAdapter = new ArrayAdapter<Country>(this, android.R.layout.simple_spinner_item, countriesList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tCountry = findViewById(R.id.editClientCountry);
        tCountry.setAdapter(dataAdapter);

        int countryPosition = 0;
        for (Country country : countriesList){
            if (country.countryId == tempCountryId)
                break;
            countryPosition++;
        }
        tCountry.setSelection(countryPosition);
    }

    public void saveClient (View view) throws JSONException, IOException {
        Boolean taxPayer;
        if (tTaxPayer.isChecked()){
            taxPayer = true;
        }
        else {
            taxPayer = false;
        }
        Spinner countrySpinner = findViewById(R.id.editClientCountry);
        Country country = (Country) countrySpinner.getSelectedItem();

        //fill json object
        JSONObject editClient = new JSONObject();
        editClient.put("name", tName.getText() + " " + tSurname.getText());
        editClient.put("id", clientId);
        editClient.put("type", tType.getSelectedItem().toString());
        editClient.put("registrationNumber", tRegistrationNumber.getText().toString());
        editClient.put("email", tEmail.getText().toString());
        editClient.put("phoneNumber", tPhone.getText().toString());
        editClient.put("taxNumber", tTaxNumber.getText().toString());
        editClient.put("taxPayer", tTaxPayer.isChecked());
        editClient.put("streetName", tStreetName.getText().toString()); //!
        editClient.put("streetNumber", tStreetNumber.getText().toString());
        editClient.put("postNumber", tPostNumber.getText().toString());
        editClient.put("city", tCity.getText().toString());
        editClient.put("countryId", country.countryId.toString());

        String cli = editClient.toString();
        Response response = update(cli);

        if (response.code() == 201 || response.code() == 200 || response.code() == 204){ //201 - created, 200 - Ok, 204 - no content (pri updateu - je kul)
            openDisplayClientActivity();
        }
        else {
            openDialog(response);
        }
    }

    public Response update(String json) throws IOException {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(getString(R.string.clients) + "/" + clientId)
                .put(body)
                .build();

        Response response = httpClient.newCall(request).execute();
        return response;
    }

    public void openDisplayClientActivity() {
        Intent intent = new Intent(this, ClientActivity.class);
        startActivity(intent);
    }

    public void openDialog(Response response){
        ResponseErrorDialog responseDialog = new ResponseErrorDialog(response);
        responseDialog.show(getSupportFragmentManager(), "Response dialog");
    }
}
