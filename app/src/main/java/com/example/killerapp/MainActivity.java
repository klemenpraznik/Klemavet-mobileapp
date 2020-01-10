package com.example.killerapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.killerapp.models.Address;
import com.example.killerapp.models.Client;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class MainActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    private TextView osebe;
    private FloatingActionButton addClientButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        osebe = (TextView) findViewById(R.id.osebe);

        addClientButton = (FloatingActionButton) findViewById(R.id.addClientButton);
        addClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openClientInsertActivity();
            }
        });
    }

    public void openClientInsertActivity() {
        Intent intent = new Intent(this, insertClientActivity.class);
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

    public void prikaziOsebe(View view) {
        if (view != null) {
            String url = "https://vodenje-racunov20191204085122.azurewebsites.net/api/clients";
            JsonArrayRequest request = new JsonArrayRequest(url, jsonArrayListener, errorListener);
            requestQueue.add(request);
        }
    }
}