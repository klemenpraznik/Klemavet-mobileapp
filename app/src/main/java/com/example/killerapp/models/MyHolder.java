package com.example.killerapp.models;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.killerapp.ItemClickListener;
import com.example.killerapp.R;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;


public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    ImageView mImageView;
    TextView mName, mMail, mPhone;
    ItemClickListener itemClickListener;

    MyHolder(@NonNull View view){
        super(view);
        this.mImageView = view.findViewById(R.id.imageIv);
        this.mName = view.findViewById(R.id.titleTv);
        this.mMail = view.findViewById(R.id.descriptionTv);

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
