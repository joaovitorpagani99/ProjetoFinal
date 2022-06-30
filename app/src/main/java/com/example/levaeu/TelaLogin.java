package com.example.levaeu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.w3c.dom.Document;

public class TelaLogin extends AppCompatActivity {
    Boolean carroConfirm;
    String usuarioID;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button cadastrar_login;
    Button entrar;
    EditText login_usuario,login_senha;
    String[] msg = {"Prencha todos os campos","Login efetuado com sucesso"};
    String nome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);
        cadastrar_login = findViewById(R.id.cadastrar);
        entrar = findViewById(R.id.acessar);
        login_usuario = findViewById(R.id.usuario);
        login_senha = findViewById(R.id.senha);
    }
    public void OnClickCadastrar(View view){
        Intent intent = new Intent(this, Tela_Cad.class);
        startActivity(intent);
    }
    public void onClickAcessar(View view){
        String usuario = login_usuario.getText().toString();
        String senha = login_senha.getText().toString();
        if(usuario.isEmpty() || senha.isEmpty()){
            Snackbar snackbar = Snackbar.make(view, msg[0], Snackbar.LENGTH_SHORT);
            snackbar.setBackgroundTint(Color.WHITE);
            snackbar.setTextColor(Color.BLACK);
            snackbar.show();
        }else{
            autenticar(view);
        }
    }
    private void autenticar(View view){
        String usuario = login_usuario.getText().toString();
        String senha = login_senha.getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(usuario,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    TelaPrincipal();
                }else{
                    String erro;
                    try {
                        throw task.getException();
                    }catch (Exception e){
                        erro = "Erro ao logar";
                    }
                    Snackbar snackbar = Snackbar.make(view, erro, Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();
        if (usuarioAtual != null){
            TelaPrincipal();
        }
    }


    public void TelaPrincipal(){
        Intent intent = new Intent(TelaLogin.this,MainActivity.class);
        startActivity(intent);
    }
}

