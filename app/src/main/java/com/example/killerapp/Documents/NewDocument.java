package com.example.killerapp.Documents;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.killerapp.R;

public class NewDocument extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_document);
        setTitle("Nov dokument");
    }
}
