package com.example.killerapp.Documents;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.killerapp.Clients.ClientActivity;
import com.example.killerapp.R;
import com.example.killerapp.models.Document;
import com.example.killerapp.models.DocumentAdapter;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class DocumentActivity extends AppCompatActivity {
    private final OkHttpClient httpClient = new OkHttpClient();
    private List<Document> documentsList = new ArrayList<>();
    RecyclerView recView;
    DocumentAdapter docAdapter;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        setTitle(R.string.invoice_title);
        getDocumentsList();

        recView = findViewById(R.id.invoiceRecyclerView);
        recView.setLayoutManager(new LinearLayoutManager(this));

        docAdapter = new DocumentAdapter(this, getArrayList(documentsList));
        recView.setAdapter(docAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getDocumentsList(){
        Request request = new Request.Builder()
                .url(getString(R.string.documents))
                .build();
        try (okhttp3.Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Document[] documents = new ObjectMapper().readValue(response.body().string(), Document[].class);
            documentsList = Arrays.asList(documents);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openClientActivity(View v) {
        Intent intent = new Intent(this, ClientActivity.class);
        startActivity(intent);
    }

    public ArrayList<Document> getArrayList (List<Document> documentsList){
        ArrayList documentArrayList = new ArrayList<Document>();
        for (Document document : documentsList){
            documentArrayList.add(document);
        }
        return documentArrayList;
    }
}
