package com.example.killerapp.Clients;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.killerapp.R;
import com.example.killerapp.Dialogs.ResponseErrorDialog;
import com.example.killerapp.models.Country;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Getter
@Setter
public class InsertClientActivity extends AppCompatActivity {
    private final OkHttpClient httpClient = new OkHttpClient();
    public List<Country> countriesList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_client);

        Spinner clientType = findViewById(R.id.clientType);
        ArrayAdapter<String> clientTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.client_types));
        clientTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clientType.setAdapter(clientTypeAdapter);

        final EditText taxNumber = findViewById(R.id.clientTaxNumber);
        final EditText registrationNumber = findViewById(R.id.clientRegistrationNumber);


        final CheckBox taxCheckBox = findViewById(R.id.taxCheckBox);
        taxCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (taxCheckBox.isChecked()) {
                    taxNumber.setEnabled(true);
                    registrationNumber.setEnabled(true);
                }
                else {
                    taxNumber.setText("");
                    registrationNumber.setText("");
                    taxNumber.setEnabled(false);
                    registrationNumber.setEnabled(false);
                }
            }
        });

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

        Spinner countrySpinner = findViewById(R.id.clientCountry);
        countrySpinner.setAdapter(dataAdapter);
    }

    public void addClient(View v) throws JSONException, IOException {
        EditText clientName = findViewById(R.id.clientName);
        Spinner clientType = findViewById(R.id.clientType);
        EditText clientSurname = findViewById(R.id.clientSurname);
        EditText clientTaxNumber = findViewById(R.id.clientTaxNumber);
        EditText clientEmail = findViewById(R.id.clientEmail);
        EditText clientStreetNumber = findViewById(R.id.clientStreetNumber);
        EditText clientCity = findViewById(R.id.clientCity);
        EditText clientStreetName = findViewById(R.id.clientStreetName);
        EditText clientRegistrationNumber = findViewById(R.id.clientRegistrationNumber);
        EditText clientPhoneNumber = findViewById(R.id.clientPhoneNumber);
        EditText clientPostNumber = findViewById(R.id.clientPostNumber);
        CheckBox taxCheckBox = findViewById(R.id.taxCheckBox);
        Spinner countrySpinner = findViewById(R.id.clientCountry);
        Country country = (Country) countrySpinner.getSelectedItem();


        //fill json object
        JSONObject newClient = new JSONObject();
        newClient.put("name", clientName.getText() + " " + clientSurname.getText());
        newClient.put("type", clientType.getSelectedItem().toString());
        newClient.put("registrationNumber", clientRegistrationNumber.getText().toString());
        newClient.put("email", clientEmail.getText().toString());
        newClient.put("phoneNumber", clientPhoneNumber.getText().toString());
        newClient.put("taxNumber", clientTaxNumber.getText().toString());
        newClient.put("taxPayer", taxCheckBox.isChecked());
        newClient.put("streetName", clientStreetName.getText().toString());
        newClient.put("streetNumber", clientStreetNumber.getText().toString());
        newClient.put("postNumber", clientPostNumber.getText().toString());
        newClient.put("city", clientCity.getText().toString());
        newClient.put("countryId", country.countryId.toString());


        //create http post request
        String cli = newClient.toString();
        Response response = post(cli);

        if (response.code() == 201 || response.code() == 200){ //201 - created, 200 - Ok
            openDisplayClientActivity();
        }
        else {
            openDialog(response);
        }
    }

    public Response post(String json) throws IOException {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(getString(R.string.clients))
                .post(body)
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

