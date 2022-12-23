package com.example.dowhile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TelaActivityCorr extends AppCompatActivity {

    private Button dirMarcacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_segunda_tela);

        dirMarcacao = findViewById(R.id.buttonDirMarcacao);
        registrarEventos();
    }

    private void registrarEventos() {

        dirMarcacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  { trocaActivMarcarcao();
            }
        });
    }

    private void trocaActivMarcarcao() {

        Intent transJanela3 = new Intent(TelaActivityCorr.this,MarcacaoActivity.class);
        startActivity(transJanela3);
    }
}