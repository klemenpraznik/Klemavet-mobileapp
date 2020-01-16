package com.example.killerapp.models;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.killerapp.R;

public class ArticleHolder extends RecyclerView.ViewHolder{

    TextView aName, aDesc, aPrice, aQty, aDiscount, aVat;


    ArticleHolder(@NonNull View view) {
        super(view);
        this.aName = view.findViewById(R.id.articleName);
        this.aDesc = view.findViewById(R.id.articleDesc);
        this.aPrice = view.findViewById(R.id.articlePrice);
        this.aQty = view.findViewById(R.id.articleQuantity);
        this.aDiscount = view.findViewById(R.id.articleDiscount);
        //this.aVat = view.findViewById(R.id.articleVAT);
    }
}
