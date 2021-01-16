package com.example.juegomemoria;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class FinJuego extends AppCompatActivity {
    private int points, record, difficulty,errors;
    private String user;
    private static final String TAG = "MyActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin_juego);

        user = getIntent().getStringExtra("user");
        points = getIntent().getIntExtra("points", 0);
        record = getIntent().getIntExtra("record", 0);
        difficulty = getIntent().getIntExtra("difficulty", 0);
        errors = getIntent().getIntExtra("errors", 0);

        Log.i(TAG,"USER"+user);
        Log.i(TAG,"PUNTOS"+points);
        Log.i(TAG,"RECORD"+record);
        Log.i(TAG,"DIFICULTAD"+difficulty);
        Log.i(TAG,"ERRORES"+errors);
    }
}