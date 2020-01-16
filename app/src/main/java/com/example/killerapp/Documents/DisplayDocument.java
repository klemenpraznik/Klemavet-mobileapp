package com.example.killerapp.Documents;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.killerapp.R;
import com.example.killerapp.models.Article;
import com.example.killerapp.models.ArticleAdapter;
import com.example.killerapp.models.Client;
import com.example.killerapp.models.Document;
import com.example.killerapp.models.DocumentAdapter;

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
    private List<Article> articleList = new ArrayList<>();
    private Integer clientId, documentId;
    TextView docDetailName, docDetailType, docPhone, docMail, docAddress, price1, discount, amountWithoutVat, documentAmountIncludingVat, paidAmount;
    ProgressBar progressBar;
    RecyclerView articlesRecyclerView;
    ArticleAdapter articleAdapter;

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
        //recicler view
        articlesRecyclerView = findViewById(R.id.displayArticles);
        articlesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        articleAdapter = new ArticleAdapter(this, getArrayList(articleList));
        articlesRecyclerView.setAdapter(articleAdapter);

        //other fields
        docDetailName = findViewById(R.id.docDetailName);
        docDetailType = findViewById(R.id.docDetailType);
        docPhone = findViewById(R.id.docPhone);
        docMail = findViewById(R.id.docMail);
        docAddress = findViewById(R.id.docAddress);
        price1 = findViewById(R.id.documentPrice1);
        discount = findViewById(R.id.documentDiscount);
        amountWithoutVat = findViewById(R.id.documentAmountWithoutVat);
        documentAmountIncludingVat = findViewById(R.id.documentAmountIncludingVat);
        paidAmount = findViewById(R.id.paidAmount);
        progressBar = findViewById(R.id.progressBar);
    }

    public void fillValues(){
        docDetailName.setText(document.getClient().name);
        docDetailType.setText(document.getClient().type);
        docPhone.setText(document.getClient().phoneNumber);
        docMail.setText(document.getClient().email);
        docAddress.setText(document.getClient().getAddress());
        price1.setText("Skupaj brez DDV: " + String.valueOf(document.getTotalExcludingVAT()) + " €");
        discount.setText("Popust " + String.valueOf(document.getDiscountAmount()) + " €");
        amountWithoutVat.setText("Znesek brez DDV: " + String.valueOf(document.getAmountExcludingVAT()) + " €");
        documentAmountIncludingVat.setText("Znesek z DDV: " + String.valueOf(document.getAmountIncludingVAT()) + " €");
        paidAmount.setText(String.valueOf(document.getPaidAmount()) + " / " + String.valueOf(document.getAmountIncludingVAT()));

        calculateProgress();
    }

    public void calculateProgress(){
        Double paid = document.getPaidAmount();
        //Double paid = 580.35;
        double paidd = 0;
        try {
            paidd = paid;
        }
        catch (Exception e){
            paidd = 0;
        }
        double required = document.getAmountIncludingVAT();
        paidAmount.setText(paidd + " / " + required);
        double paidPercent = paidd * 100 /  required;
        if (paidPercent == 100){
            progressBar.getProgressDrawable().setColorFilter(
                    Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);
            progressBar.setProgress((int) paidPercent);
        }
        else if (paidPercent < 50){
            progressBar.getProgressDrawable().setColorFilter(
                    Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
            progressBar.setProgress((int) paidPercent);
        }
        else if (paidPercent > 50 && paidPercent < 100){
            progressBar.getProgressDrawable().setColorFilter(
                    Color.YELLOW, android.graphics.PorterDuff.Mode.SRC_IN);
            progressBar.setProgress((int) paidPercent);
        }

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
            articleList = Arrays.asList(articles);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Article> getArrayList (List<Article> articlesList){
        ArrayList articleArrayList = new ArrayList<Article>();
        for (Article article : articlesList){
            articleArrayList.add(article);
        }
        return articleArrayList;
    }
}
