package com.example.killerapp.models;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.killerapp.ItemClickListener;
import com.example.killerapp.R;

import lombok.NonNull;


public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    ImageView mImageView;
    TextView mName, mMail, mPhone;
    ItemClickListener itemClickListener;

    MyHolder(@NonNull View view){
        super(view);
        this.mImageView = view.findViewById(R.id.imageDispl);
        this.mName = view.findViewById(R.id.nameDispl);
        this.mMail = view.findViewById(R.id.emailDispl);
        this.mPhone = view.findViewById(R.id.phoneDispl);

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
