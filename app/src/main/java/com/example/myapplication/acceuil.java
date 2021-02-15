package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class acceuil extends AppCompatActivity {

    Button _btnRoman , _btnAction, _btnhorreur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acceuil);

        _btnRoman = findViewById(R.id.btnRoman);
        _btnAction = findViewById(R.id.btnAction);
        _btnhorreur = findViewById(R.id.btnhorreur);

        _btnRoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),romance.class));
            }
        });

        _btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), action_ajout.class));
            }
        });

        _btnhorreur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),horreur.class));
            }
        });

    }
}
