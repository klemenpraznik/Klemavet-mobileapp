package com.example.killerapp.Documents;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.example.killerapp.R;
import com.example.killerapp.models.Article;
import com.example.killerapp.models.Client;
import com.example.killerapp.models.Document;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class DisplayDocument extends AppCompatActivity {
    private final OkHttpClient httpClient = new OkHttpClient();
    private Document document;
    private List<Article> articleList;
    private Integer clientId, documentId;
    TextView docDetailName, docDetailType, docPhone, docMail, docAddress;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_document);

        Intent intent = getIntent();
        clientId = intent.getIntExtra("iClientId", 0);
        documentId = intent.getIntExtra("iDocumentId", 0);

        //podatki iz API-ja
        getDocument(documentId);
        getClient(document.getClientId());
        getDocumentArticles(documentId);

        //definirana polja
        setTitle("Račun št.: " + documentId);

        //napolnimo vrednosti
        setViewFields();
        fillValues();
    }

    public void setViewFields(){
        docDetailName = findViewById(R.id.docDetailName);
        docDetailType = findViewById(R.id.docDetailType);
        docPhone = findViewById(R.id.docPhone);
        docMail = findViewById(R.id.docMail);
        docAddress = findViewById(R.id.docAddress);
    }

    public void fillValues(){
        docDetailName.setText(document.getClient().name);
        docDetailType.setText(document.getClient().type);
        docPhone.setText(document.getClient().phoneNumber);
        docMail.setText(document.getClient().email);
        docAddress.setText(document.getClient().getAddress());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getClient(Integer clientId){
        Request request = new Request.Builder()
                .url(getString(R.string.clients) + "/" + clientId)
                .build();
        try (okhttp3.Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            document.setClient(new ObjectMapper().readValue(response.body().string(), Client.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getDocument(Integer documentId){
        Request request = new Request.Builder()
                .url(getString(R.string.documents) + "/" + documentId)
                .build();

        try (okhttp3.Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            document = new ObjectMapper().readValue(response.body().string(), Document.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Bom dokončou samo rabm nov api dt v azure k še nimam updejtane verzije gor

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getDocumentArticles(Integer documentId){
        Request request = new Request.Builder()
                .url(getString(R.string.articles) + "/" + documentId)
                .build();

        try (okhttp3.Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Article[] articles = new ObjectMapper().readValue(response.body().string(), Article[].class);
            articleList = getArrayList(articles);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Article> getArrayList (Article[] clientsList){
        ArrayList articleArrayList = new ArrayList<Article>();
        for (Article article : clientsList){
            articleArrayList.add(article);
        }
        return articleArrayList;
    }
}
