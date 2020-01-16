package com.example.killerapp.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.killerapp.R;

import java.util.ArrayList;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleHolder>{
    Context c;
    ArrayList<Article> articles;

    public ArticleAdapter(Context c, ArrayList<Article> models){
        this.c = c;
        this.articles = models;
    }

    @NonNull
    @Override
    public ArticleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_row, null);

        return new ArticleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleHolder holder, int position) {
        holder.aName.setText(articles.get(position).getProduct().getShortName());
        holder.aDesc.setText(articles.get(position).getProduct().getDescription());
        holder.aPrice.setText(String.valueOf(articles.get(position).getPrice()) + " €");
        holder.aQty.setText(String.valueOf((int)articles.get(position).getQuantity()) + "x");
        holder.aDiscount.setText(String.valueOf(articles.get(position).getDiscount()) + " %");
        //holder.aVat.setText(String.valueOf(articles.get(position).getTaxRate()) + " %");
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}
