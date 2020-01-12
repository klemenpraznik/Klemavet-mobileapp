package com.example.killerapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class DisplayClient extends AppCompatActivity {

    TextView mTitleTv, mDescTv, mTypeTv, mPhoneTv, mAddressTv, mTaxNumberTv, mRegistrationNumberTv;
    ImageView mImageIv;
    RadioButton mTaxPayerTv;
    Boolean mTaxPayer;
    Integer clientId, mCountryId;
    String mTitle, mMail, mPhone, mAddress, mRegistrationNumber, mTaxNumber, mType, mStreetNumber, mPostNumber, mCity, mCountry, mStreetName;

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

        mTitle = intent.getStringExtra("iTitle");
        mMail = intent.getStringExtra("iMail");
        mPhone = intent.getStringExtra("iPhone");
        mStreetName = intent.getStringExtra("iStreetName");
        mStreetNumber = intent.getStringExtra("iStreetNumber");
        mPostNumber = intent.getStringExtra("iPostNumber");
        mCity = intent.getStringExtra("iCity");
        mCountry = intent.getStringExtra("iCountry");
        mCountryId = intent.getIntExtra("iCountryId", 1);
        mRegistrationNumber = intent.getStringExtra("iRegistrationNumber");
        mTaxNumber = intent.getStringExtra("iTaxNumber");
        mType = intent.getStringExtra("iType");
        mTaxPayer = intent.getBooleanExtra("iTaxPayer", false);

        actionBar.setTitle(mTitle);

        mTitleTv.setText(mTitle);
        mDescTv.setText(mMail);
        mTypeTv.setText(mType);
        mPhoneTv.setText(mPhone);
        mAddressTv.setText(mStreetName + " " + mStreetNumber + ", " + mPostNumber + " " + mCity + ", " + mCountry);
        mTaxNumberTv.setText(mTaxNumber);
        mRegistrationNumberTv.setText(mRegistrationNumber);
        mTaxPayerTv.setChecked(mTaxPayer);
        mTaxPayerTv.setEnabled(false);

        clientId = intent.getIntExtra("iClientId", 0);
    }

    public void editClient(View view){
        Intent intent = new Intent(getBaseContext(), EditClient.class);
        intent.putExtra("EDIT_CLIENT_ID", this.clientId);
        intent.putExtra("iTitle", mTitle); //Ime in priimek
        intent.putExtra("iMail", mMail);
        intent.putExtra("iPhone", mPhone);
        intent.putExtra("iStreetName", mStreetName);
        intent.putExtra("iStreetNumber", mStreetNumber);
        intent.putExtra("iPostNumber", mPostNumber);
        intent.putExtra("iCity", mCity);
        intent.putExtra("iCountry", mCountry);
        intent.putExtra("iCountryId", mCountryId);
        intent.putExtra("iRegistrationNumber", mRegistrationNumber);
        intent.putExtra("iTaxNumber", mTaxNumber);
        intent.putExtra("iType", mType);
        intent.putExtra("iTaxPayer", mTaxPayer);
        startActivity(intent);
    }
}
