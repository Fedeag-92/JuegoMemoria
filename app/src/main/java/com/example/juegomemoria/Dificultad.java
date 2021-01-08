package com.example.juegomemoria;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class Dificultad extends AppCompatActivity {

    Button btnFacil;
    Button btnNormal;
    Button btnDificil;
    Button btnJugar;
    Button btnSonido;
    Button btnRanking;
    ImageView btnAtras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dificultad);

        btnFacil = (Button) findViewById(R.id.btnFacil);
        btnNormal = (Button) findViewById(R.id.btnNormal);
        btnDificil = (Button) findViewById(R.id.btnDificil);
        btnJugar = (Button) findViewById(R.id.btnJugar);
        btnSonido = (Button) findViewById(R.id.btnSonido);
        btnRanking = (Button) findViewById(R.id.btnRanking);
        btnAtras = (ImageView) findViewById(R.id.btnAtras);
    }
}