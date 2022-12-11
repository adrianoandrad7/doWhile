package com.example.dowhile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    private Button entrarLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        entrarLogin = findViewById(R.id.buttonEntrar);
        registrarEventos();

    }

    private void registrarEventos(){

        entrarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trocaActivEntrar();
            }

        });
    }

    private void trocaActivEntrar(){

        Intent transJanela2 = new Intent(LoginActivity.this,SegundaTelaActivity.class);
        startActivity(transJanela2);

    }
}