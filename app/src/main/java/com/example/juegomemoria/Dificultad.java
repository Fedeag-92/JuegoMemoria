package com.example.juegomemoria;

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

    Button btnEasy;
    Button btnNormal;
    Button btnHard;
    Button btnPlay;
    ToggleButton btnSound;
    Button btnRanking;
    ImageView btnBack;
    int choice = 0;
    AudioManager amanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dificultad);

        amanager = (AudioManager)getSystemService(AUDIO_SERVICE);

        btnEasy = (Button) findViewById(R.id.btnFacil);
        btnNormal = (Button) findViewById(R.id.btnNormal);
        btnHard = (Button) findViewById(R.id.btnDificil);
        btnPlay = (Button) findViewById(R.id.btnJugar);
        btnSound = (ToggleButton) findViewById(R.id.btnSonido);
        btnRanking = (Button) findViewById(R.id.btnRanking);
        btnBack = (ImageView) findViewById(R.id.btnAtras);
        btnSound.setChecked(true);

        btnEasy.setOnClickListener(this);
        btnNormal.setOnClickListener(this);
        btnHard.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        btnSound.setOnClickListener(this);
        btnRanking.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnFacil:
                choice = 1;
                btnEasy.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifActivado));
                btnNormal.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifDesactivado));
                btnHard.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifDesactivado));
                break;
            case R.id.btnNormal:
                choice = 2;
                btnEasy.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifDesactivado));
                btnNormal.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifActivado));
                btnHard.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifDesactivado));
                break;
            case R.id.btnDificil:
                choice = 3;
                btnEasy.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifDesactivado));
                btnNormal.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifDesactivado));
                btnHard.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifActivado));
                break;
            case R.id.btnJugar:
                if (choice != 0) {
                    Intent i = new Intent(Dificultad.this, Juego.class);
                    i.putExtra("user", getIntent().getExtras().getString("user"));
                    i.putExtra("choice", this.choice);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Debes elegir una dificultad", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.btnSonido:
                amanager.setStreamMute(AudioManager.STREAM_MUSIC, !btnSound.isChecked());
                break;
            case R.id.btnRanking:
                Intent i = new Intent(Dificultad.this, Ranking.class);
                startActivity(i);
                break;
            case R.id.btnAtras:
                onBackPressed();
                break;
        }
    }
}