package com.example.killerapp;


import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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

public class MainActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    private TextView osebe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        osebe = (TextView) findViewById(R.id.osebe);
    }

    private Response.Listener<JSONArray> jsonArrayListener = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            ArrayList<String> data = new ArrayList<>();
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject object = response.getJSONObject(i);
                    String name = object.getString("name");
                    String email = object.getString("email");
//                    String priimek = "xxx";
//                    String id = object.getString("id");
//                    String mesto = object.getString("address");
//                    String naslov = object.getString("email");
                    data.add(name + ", " + email);
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