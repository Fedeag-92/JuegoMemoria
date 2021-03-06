package com.example.juegomemoria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.media.MediaPlayer;

public class Dificultad extends AppCompatActivity implements View.OnClickListener {
    private Button btnEasy;
    private Button btnNormal;
    private Button btnHard;
    private Button btnPlay;
    private TextView description;
    private ToggleButton btnSound;
    private Button btnRanking;
    private ImageView btnBack, imgBart;
    private int choice = 0;
    private TextView tittleGame, tittleChoice;
    private ConstraintLayout.LayoutParams paramsBart, paramsDescription;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dificultad);

        //Vinculacion de variables con objetos graficos
        description = (TextView) findViewById(R.id.difficultyInfo);
        tittleGame = (TextView) findViewById(R.id.tittleDificultad);
        tittleChoice = (TextView) findViewById(R.id.textChoiceDifficulty);
        btnEasy = (Button) findViewById(R.id.btnFacil);
        btnNormal = (Button) findViewById(R.id.btnNormal);
        btnHard = (Button) findViewById(R.id.btnDificil);
        btnPlay = (Button) findViewById(R.id.btnJugar);
        btnSound = (ToggleButton) findViewById(R.id.btnSonido);
        btnRanking = (Button) findViewById(R.id.btnRanking);
        btnBack = (ImageView) findViewById(R.id.btnBackD);
        imgBart = (ImageView) findViewById(R.id.bartDif);

        //Seteo de fuente de titulos
        tittleGame.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/simpson.ttf"));
        tittleChoice.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/simpson.ttf"));

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
                moverSeleccion(R.string.descripcionFacil,R.id.btnFacil);
                choice = 1;
                btnEasy.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifActivado));
                btnNormal.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifDesactivado));
                btnHard.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifDesactivado));
                break;
            case R.id.btnNormal:
                moverSeleccion(R.string.descripcionNormal,R.id.btnNormal);
                choice = 2;
                btnEasy.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifDesactivado));
                btnNormal.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifActivado));
                btnHard.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifDesactivado));
                break;
            case R.id.btnDificil:
                moverSeleccion(R.string.descripcionDificil,R.id.btnDificil);
                choice = 3;
                btnEasy.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifDesactivado));
                btnNormal.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifDesactivado));
                btnHard.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.btnDifActivado));
                break;
            case R.id.btnJugar:
                if (choice != 0) {
                    mp = MediaPlayer.create(Dificultad.this, R.raw.feo);
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

    @Override
    public void onResume(){
        super.onResume();
        btnSound.setChecked(MainActivity.getMediaPlayer().isPlaying());
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Dificultad.this);
        builder.setMessage("¿Deseas salir de la aplicación o cerrar sesión?");
        builder.setCancelable(true);

        builder.setNegativeButton("Cerrar sesión", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Dificultad.super.onBackPressed();
                mp = MediaPlayer.create(Dificultad.this, R.raw.vuelva);
                mp.start();
                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAffinity();
                System.exit(0);
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void moverSeleccion(int info, int idButton){
        description.setText(info);
        paramsDescription = getParamsDescription();
        paramsDescription.topToTop = idButton;
        paramsDescription.bottomToBottom = idButton;
        paramsBart = getParamsBart();
        paramsBart.topToTop = idButton;
        paramsBart.bottomToBottom = idButton;
        imgBart.requestLayout();
    }
}