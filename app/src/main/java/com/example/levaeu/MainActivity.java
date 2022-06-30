package com.example.levaeu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.service.controls.actions.BooleanAction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.w3c.dom.Document;


public class MainActivity extends AppCompatActivity {
    private TextView mensagem;
    private View btn_nova_Carona;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String usuarioID,nome;
    Boolean carroConfirm  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mensagem = findViewById(R.id.mensagem);

        btn_nova_Carona = findViewById(R.id.nova_carona);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Infla o menu com os botões da actionbar
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sair){
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, TelaLogin.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if(documentSnapshot != null){
                    carroConfirm = documentSnapshot.getBoolean("possui carro");
                    nome = documentSnapshot.getString("nome");
                    mensagem.setText("Bem vindo! " + nome);
                    btn_nova_Carona.setOnClickListener(new View.OnClickListener() {
                        Intent intent = new Intent(MainActivity.this, tela_Motorista.class);
                        Intent intent2 = new Intent(MainActivity.this, tela_Passageiro.class);
                        @Override
                        public void onClick(View view) {
                            if (carroConfirm == true)
                                startActivity(intent);
                            if (carroConfirm == false)
                                startActivity(intent2);
                        }
                    });
                }
            }
        });
    }
}