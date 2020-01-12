package com.example.killerapp;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.StrictMode;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    private TextView osebe;
    private FloatingActionButton addClientButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        openDisplayClientActivity(); //odpre DisplayClient activity

    }

    public void openDisplayClientActivity() {
        Intent intent = new Intent(this, DisplayClients.class);
        startActivity(intent);
    }
}