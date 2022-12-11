package com.example.dowhile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SegundaTelaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_segunda_tela);
    }
}