package com.example.killerapp;

import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.killerapp.models.Country;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Getter
@Setter
public class InsertClientActivity extends AppCompatActivity {
    private final OkHttpClient httpClient = new OkHttpClient();
    public List<Country> countriesList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_client);

        Spinner clientType = (Spinner) findViewById(R.id.clientType);
        ArrayAdapter<String> clientTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.client_types));
        clientTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clientType.setAdapter(clientTypeAdapter);

        final EditText taxNumber = findViewById(R.id.clientTaxNumber);
        final EditText registrationNumber = findViewById(R.id.clientRegistrationNumber);

        final CheckBox taxCheckBox = (CheckBox) findViewById(R.id.taxCheckBox);
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

        String url = "https://vodenje-racunov20191204085122.azurewebsites.net/api/country";
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

}
