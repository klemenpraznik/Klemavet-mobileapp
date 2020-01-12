package com.example.killerapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayClient extends AppCompatActivity {

    TextView mTitleTv, mDescTv;
    ImageView mImageIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_client);

        //back button
        ActionBar actionBar = getSupportActionBar();

        mTitleTv = findViewById(R.id.title);
        mDescTv = findViewById(R.id.description);
        mImageIv = findViewById(R.id.imageView);

        Intent intent = getIntent();

        String mTitle = intent.getStringExtra("iTitle");
        String mMail = intent.getStringExtra("iMail");

        actionBar.setTitle(mTitle);

        mTitleTv.setText(mTitle);
        mDescTv.setText(mMail);
    }
}
