package com.example.killerapp.Clients;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.killerapp.R;
import com.example.killerapp.models.Client;
import com.example.killerapp.models.MyAdapter;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ClientActivity extends AppCompatActivity {
    private final OkHttpClient httpClient = new OkHttpClient();
    private List<Client> clientsList = new ArrayList<>();
    RecyclerView recyclerView;
    MyAdapter myAdapter;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        setTitle(R.string.clients_title);
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

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new MyAdapter(this, getArrayList(clientsList));
        recyclerView.setAdapter(myAdapter);
    }

    public void openDisplayInsertActivity(View v) {
        Intent intent = new Intent(this, InsertClientActivity.class);
        startActivity(intent);
    }

    public ArrayList<Client> getArrayList (List<Client> clientsList){
        ArrayList clientArrayList = new ArrayList<Client>();
        for (Client client : clientsList){
            clientArrayList.add(client);
        }
        return clientArrayList;
    }
}
