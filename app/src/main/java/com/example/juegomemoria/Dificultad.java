package com.example.juegomemoria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
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
    ImageView btnBack, imgBart;
    int choice = 0;
    AudioManager amanager;
    SharedPreferences preferences;
    ConstraintLayout.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dificultad);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        amanager = (AudioManager) getSystemService(AUDIO_SERVICE);

        btnEasy = (Button) findViewById(R.id.btnFacil);
        btnNormal = (Button) findViewById(R.id.btnNormal);
        btnHard = (Button) findViewById(R.id.btnDificil);
        btnPlay = (Button) findViewById(R.id.btnJugar);
        btnSound = (ToggleButton) findViewById(R.id.btnSonido);
        btnRanking = (Button) findViewById(R.id.btnRanking);
        btnBack = (ImageView) findViewById(R.id.btnBackD);
        imgBart = (ImageView) findViewById(R.id.bartDif);

        if(!MainActivity.getMediaPlayer().isPlaying())
            btnSound.setChecked(false);

        btnEasy.setOnClickListener(this);
        btnNormal.setOnClickListener(this);
        btnHard.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        btnRanking.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnSound.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnFacil || v.getId() == R.id.btnNormal || v.getId() == R.id.btnDificil){
            imgBart.setVisibility(View.VISIBLE);
            params = (ConstraintLayout.LayoutParams) imgBart.getLayoutParams();
        }
        switch (v.getId()) {
            case R.id.btnFacil:
                params.topToTop = R.id.btnFacil;
                params.bottomToBottom = R.id.btnFacil;
                imgBart.requestLayout();
                choice = 1;
                btnEasy.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifActivado));
                btnNormal.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifDesactivado));
                btnHard.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifDesactivado));
                break;
            case R.id.btnNormal:
                params.topToTop = R.id.btnNormal;
                params.bottomToBottom = R.id.btnNormal;
                imgBart.requestLayout();
                choice = 2;
                btnEasy.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifDesactivado));
                btnNormal.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifActivado));
                btnHard.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifDesactivado));
                break;
            case R.id.btnDificil:
                params = (ConstraintLayout.LayoutParams) imgBart.getLayoutParams();
                params.topToTop = R.id.btnDificil;
                params.bottomToBottom = R.id.btnDificil;
                imgBart.requestLayout();
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
                if (!btnSound.isChecked()) {
                    MainActivity.getMediaPlayer().pause();
                } else {
                    MainActivity.getMediaPlayer().start();
                }
                break;
            case R.id.btnRanking:
                Intent i = new Intent(Dificultad.this, Ranking.class);
                startActivity(i);
                break;
            case R.id.btnBackD:
                onBackPressed();
        }
    }
}