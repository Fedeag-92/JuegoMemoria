package com.example.juegomemoria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Dificultad extends AppCompatActivity implements View.OnClickListener {
    Button btnEasy;
    Button btnNormal;
    Button btnHard;
    Button btnPlay;
    TextView description;
    ToggleButton btnSound;
    Button btnRanking;
    ImageView btnBack, imgBart;
    int choice = 0;
    ConstraintLayout.LayoutParams paramsBart, paramsDescription;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dificultad);

        description = (TextView) findViewById(R.id.difficultyInfo);
        btnEasy = (Button) findViewById(R.id.btnFacil);
        btnNormal = (Button) findViewById(R.id.btnNormal);
        btnHard = (Button) findViewById(R.id.btnDificil);
        btnPlay = (Button) findViewById(R.id.btnJugar);
        btnSound = (ToggleButton) findViewById(R.id.btnSonido);
        btnRanking = (Button) findViewById(R.id.btnRanking);
        btnBack = (ImageView) findViewById(R.id.btnBackD);
        imgBart = (ImageView) findViewById(R.id.bartDif);

        mp = MediaPlayer.create(Dificultad.this, R.raw.feo);
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
        switch (v.getId()) {
            case R.id.btnFacil:
                description.setText(R.string.descripcionFacil);
                paramsDescription = getParamsDescription();
                paramsDescription.topToTop = R.id.btnFacil;
                paramsDescription.bottomToBottom = R.id.btnFacil;
                paramsBart = getParamsBart();
                paramsBart.topToTop = R.id.btnFacil;
                paramsBart.bottomToBottom = R.id.btnFacil;
                imgBart.requestLayout();
                choice = 1;
                btnEasy.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifActivado));
                btnNormal.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifDesactivado));
                btnHard.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifDesactivado));
                break;
            case R.id.btnNormal:
                description.setText(R.string.descripcionNormal);
                paramsDescription = getParamsDescription();
                paramsDescription.topToTop = R.id.btnNormal;
                paramsDescription.bottomToBottom = R.id.btnNormal;
                paramsBart = getParamsBart();
                paramsBart.topToTop = R.id.btnNormal;
                paramsBart.bottomToBottom = R.id.btnNormal;
                imgBart.requestLayout();
                choice = 2;
                btnEasy.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifDesactivado));
                btnNormal.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifActivado));
                btnHard.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifDesactivado));
                break;
            case R.id.btnDificil:
                description.setText(R.string.descripcionDificil);
                paramsDescription = getParamsDescription();
                paramsDescription.topToTop = R.id.btnDificil;
                paramsDescription.bottomToBottom = R.id.btnDificil;
                paramsBart = getParamsBart();
                paramsBart.topToTop = R.id.btnDificil;
                paramsBart.bottomToBottom = R.id.btnDificil;
                imgBart.requestLayout();
                choice = 3;
                btnEasy.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifDesactivado));
                btnNormal.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifDesactivado));
                btnHard.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifActivado));
                break;
            case R.id.btnJugar:
                if (choice != 0) {
                    mp.start();
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

    public ConstraintLayout.LayoutParams getParamsBart(){
        if(imgBart.getVisibility() == View.INVISIBLE)
            imgBart.setVisibility(View.VISIBLE);
        return (ConstraintLayout.LayoutParams) imgBart.getLayoutParams();
    }

    public ConstraintLayout.LayoutParams getParamsDescription(){
        if(description.getVisibility() == View.INVISIBLE)
            description.setVisibility(View.VISIBLE);
        return (ConstraintLayout.LayoutParams) description.getLayoutParams();
    }

    public void OnResume(){
        super.onResume();
        btnSound.setChecked(MainActivity.getMediaPlayer().isPlaying());
    }
}