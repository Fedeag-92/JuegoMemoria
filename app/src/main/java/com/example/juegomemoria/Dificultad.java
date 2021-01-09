package com.example.juegomemoria;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Dificultad extends AppCompatActivity implements View.OnClickListener {

    Button btnFacil;
    Button btnNormal;
    Button btnDificil;
    Button btnJugar;
    ToggleButton btnSonido;
    Button btnRanking;
    ImageView btnAtras;
    int eleccion = 0;
    AudioManager amanager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dificultad);

        amanager = (AudioManager)getSystemService(AUDIO_SERVICE);

        btnFacil = (Button) findViewById(R.id.btnFacil);
        btnNormal = (Button) findViewById(R.id.btnNormal);
        btnDificil = (Button) findViewById(R.id.btnDificil);
        btnJugar = (Button) findViewById(R.id.btnJugar);
        btnSonido = (ToggleButton) findViewById(R.id.btnSonido);
        btnRanking = (Button) findViewById(R.id.btnRanking);
        btnAtras = (ImageView) findViewById(R.id.btnAtras);
        btnSonido.setChecked(true);

        btnFacil.setOnClickListener(this);
        btnNormal.setOnClickListener(this);
        btnDificil.setOnClickListener(this);
        btnJugar.setOnClickListener(this);
        btnSonido.setOnClickListener(this);
        btnRanking.setOnClickListener(this);
        btnAtras.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnFacil:
                eleccion = 1;
                btnFacil.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifActivado));
                btnNormal.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifDesactivado));
                btnDificil.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifDesactivado));
                break;
            case R.id.btnNormal:
                eleccion = 2;
                btnFacil.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifDesactivado));
                btnNormal.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifActivado));
                btnDificil.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifDesactivado));
                break;
            case R.id.btnDificil:
                eleccion = 3;
                btnFacil.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifDesactivado));
                btnNormal.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifDesactivado));
                btnDificil.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifActivado));
                break;
            case R.id.btnJugar:
                if (eleccion != 0) {
                    Intent i = new Intent(Dificultad.this, Juego.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Debes elegir una dificultad", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.btnSonido:
                if(btnSonido.isChecked())
                    amanager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                else
                    amanager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                break;
            case R.id.btnRanking:
                Intent i = new Intent(Dificultad.this, Ranking.class);
                startActivity(i);
                break;
            case R.id.btnAtras:
                onBackPressed();
                break;
        }
        ;
    }

}