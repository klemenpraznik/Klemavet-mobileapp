package com.example.killerapp;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.killerapp.models.Address;
import com.example.killerapp.models.Client;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DisplayClients extends AppCompatActivity {
    private RequestQueue requestQueue;
    private TextView osebe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_clients);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Stranke");
        setSupportActionBar(toolbar);

        osebe = (TextView) findViewById(R.id.textView);
        osebe.setText("");

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDisplayInsertActivity();
            }
        });


        prikaziOsebe();
    }

    public void prikaziOsebe() {
        String url = "https://vodenje-racunov20191204085122.azurewebsites.net/api/clients";
        JsonArrayRequest request = new JsonArrayRequest(url, jsonArrayListener, errorListener);
        requestQueue.add(request);
    }

    public void openDisplayInsertActivity() {
        Intent intent = new Intent(this, InsertClientActivity.class);
        startActivity(intent);
    }


    private Response.Listener<JSONArray> jsonArrayListener = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            ArrayList<String> data = new ArrayList<>();
            List<Client> clientList = new ArrayList<>();
            for (int i = 0; i < response.length(); i++) {
                try {
                    Client client = new Client();
                    JSONObject object = response.getJSONObject(i);

                    client.id = object.getInt("id");
                    client.name = object.getString("name");
                    client.type = object.getString("type");
                    client.registrationNumber = object.getString("registrationNumber");
                    client.email = object.getString("email");
                    client.phoneNumber = object.getString("phoneNumber");
                    client.taxNumber = object.getString("taxNumber");
                    client.taxPayer = object.getBoolean("taxPayer");

                    Address address = new Address();
                    address.streetName = object.getString("streetName");
                    address.streetNumber = object.getString("streetNumber");
                    address.postNumber = object.getString("postNumber");
                    address.city = object.getString("city");
                    address.countryId = object.getInt("countryId");
                    JSONObject countryObject = object.getJSONObject("country");
                    address.country = countryObject.getString("countryName");

                    client.address = address;
                    client.countryId = object.getInt("countryId");

                    clientList.add(client);
                    data.add(client.name + ", " + client.email);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
            }
            osebe.setText("");
            osebe.setMovementMethod(new ScrollingMovementMethod());
            for (String row: data) {
                String currentText = osebe.getText().toString();
                osebe.setText(currentText + "\n\n" + row);
            }
        }
    };

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("REST error", error.getMessage());
        }
    };

}
