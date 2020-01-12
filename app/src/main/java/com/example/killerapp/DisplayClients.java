package com.example.killerapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.killerapp.models.Client;
import com.example.killerapp.models.Country;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class DisplayClients extends AppCompatActivity {
    private TextView osebe;
    private final OkHttpClient httpClient = new OkHttpClient();
    private List<Client> clientsList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_clients);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Stranke");
        setSupportActionBar(toolbar);

        osebe = (TextView) findViewById(R.id.textView);
        osebe.setText("");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDisplayInsertActivity();
            }
        });

        //fill clientsList
        String url = getString(R.string.clients);
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (okhttp3.Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Client[] clients = new ObjectMapper().readValue(response.body().string(), Client[].class);
            clientsList = Arrays.asList(clients);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //izpis
        ArrayList<String> data = new ArrayList<>();
        for (Client client : clientsList) {
            data.add(client.name + ", " + client.email);
        }
        osebe.setText("");
        osebe.setMovementMethod(new ScrollingMovementMethod());
        for (String row: data) {
            String currentText = osebe.getText().toString();
            osebe.setText(currentText + "\n\n" + row);
        }
    }

    public void openDisplayInsertActivity() {
        Intent intent = new Intent(this, InsertClientActivity.class);
        startActivity(intent);
    }
}
