package com.example.killerapp.models;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.killerapp.ItemClickListener;
import com.example.killerapp.R;

import lombok.NonNull;

public class DocumentHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    ImageView dImageView;
    TextView dName, dMail, dPhone, dDocId;
    ItemClickListener itemClickListener;

    DocumentHolder(@NonNull View view){
        super(view);
        this.dImageView = view.findViewById(R.id.imageDispl);
        this.dName = view.findViewById(R.id.nameDispl);
        this.dMail = view.findViewById(R.id.emailDispl);
        this.dPhone = view.findViewById(R.id.phoneDispl);
        this.dDocId = view.findViewById(R.id.documentIdDispl);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClickListener(v, getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener ic){
        this.itemClickListener = ic;
    }
}
