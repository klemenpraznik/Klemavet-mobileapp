package com.example.killerapp.Documents;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.killerapp.R;
import com.example.killerapp.models.Client;
import com.example.killerapp.models.Country;
import com.example.killerapp.models.Document;
import com.example.killerapp.models.Product;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewDocument extends AppCompatActivity {
    private final OkHttpClient httpClient = new OkHttpClient();
    private List<Client> clients = new ArrayList<>();
    private List<Product> products;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_document);
        setTitle("Nov dokument");

        getClients();
        getProducts();

        ArrayAdapter<Client> dataAdapter = new ArrayAdapter<Client>(this, android.R.layout.simple_spinner_item, clients);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner clientSpinner = findViewById(R.id.newClientSpinner );
        clientSpinner.setAdapter(dataAdapter);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getClients(){
        String url = getString(R.string.clients);
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Client[] clientArray = new ObjectMapper().readValue(response.body().string(), Client[].class);
            clients = Arrays.asList(clientArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getProducts(){
        String url = getString(R.string.products);
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Product[] productArray = new ObjectMapper().readValue(response.body().string(), Product[].class);
            products = Arrays.asList(productArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public <T> ArrayList<T> getArrayList (List<T> documentsList){
//        ArrayList returnArrayList = new ArrayList<Client>();
//        for (T client : documentsList){
//            returnArrayList.add(client);
//        }
//        return returnArrayList;
//    }

}
