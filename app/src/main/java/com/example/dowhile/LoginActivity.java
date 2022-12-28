package com.example.dowhile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "CustomAuthActivity";
    private FirebaseAuth mAuth;

    private Button entrarLogin;
    private EditText editEmail, editSenha;
    private ProgressBar progressB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        entrarLogin = findViewById(R.id.buttonEntrar);
        editEmail = findViewById(R.id.email);
        editSenha = findViewById(R.id.senha);
        progressB = findViewById(R.id.progress);

        registrarEventos();
    }

    private void registrarEventos(){
        entrarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = editEmail.getText().toString();
                String senha = editSenha.getText().toString();

                if(email.isEmpty() || senha.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }else{
                    AutenticarUsuario();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {
    }

    private void AutenticarUsuario() {
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();
        mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCustomToken:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            progressB.setVisibility(View.VISIBLE);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    trocaActivEntrar();
                                }
                            },3000);
                        } else {
                            Log.w(TAG, "signInWithCustomToken:failure", task.getException());
                            System.out.println("ERRO" + task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }
    private void trocaActivEntrar(){
        Intent transJanela2 = new Intent(LoginActivity.this, TelaActivityCorr.class);
        startActivity(transJanela2);
        finish();
    }
}