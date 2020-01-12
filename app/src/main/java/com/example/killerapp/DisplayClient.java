package com.example.killerapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class DisplayClient extends AppCompatActivity {

    TextView mTitleTv, mDescTv, mTypeTv, mPhoneTv, mAddressTv, mTaxNumberTv, mRegistrationNumberTv;
    ImageView mImageIv;
    RadioButton mTaxPayerTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_client);

        //back button
        ActionBar actionBar = getSupportActionBar();

        mTitleTv = findViewById(R.id.title);
        mTypeTv = findViewById(R.id.type);
        mPhoneTv = findViewById(R.id.phone);
        mAddressTv = findViewById(R.id.address);
        mTaxNumberTv = findViewById(R.id.taxNumber);
        mRegistrationNumberTv = findViewById(R.id.clientRegistrationNumber);
        mDescTv = findViewById(R.id.description);
        mImageIv = findViewById(R.id.imageView);
        mTaxPayerTv = findViewById(R.id.taxPayer);

        Intent intent = getIntent();

        String mTitle = intent.getStringExtra("iTitle");
        String mMail = intent.getStringExtra("iMail");
        String mPhone = intent.getStringExtra("iPhone");
        String mAddress = intent.getStringExtra("iAddress");
        String mRegistrationNumber = intent.getStringExtra("iRegistrationNumber");
        String mTaxNumber = intent.getStringExtra("iTaxNumber");
        String mType = intent.getStringExtra("iType");
        Boolean mTaxPayer = intent.getBooleanExtra("iTaxPayer", false);

        actionBar.setTitle(mTitle);

        mTitleTv.setText(mTitle);
        mDescTv.setText(mMail);
        mTypeTv.setText(mType);
        mPhoneTv.setText(mPhone);
        mAddressTv.setText(mAddress);
        mTaxNumberTv.setText(mTaxNumber);
        mRegistrationNumberTv.setText(mRegistrationNumber);
        mTaxPayerTv.setChecked(mTaxPayer);
        mTaxPayerTv.setEnabled(false);
    }
}
