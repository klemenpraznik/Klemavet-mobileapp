package com.example.killerapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.StrictMode;

import com.example.killerapp.Clients.ClientActivity;
import com.example.killerapp.Documents.DocumentActivity;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //openClientActivity(); //odpre DisplayClient activity
        openInvoiceActivity();
    }

    public void openClientActivity() {
        Intent intent = new Intent(this, ClientActivity.class);
        startActivity(intent);
    }

    public void openInvoiceActivity() {
        Intent intent = new Intent(this, DocumentActivity.class);
        startActivity(intent);
    }


}