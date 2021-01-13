package com.example.juegomemoria;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

public class Ranking extends AppCompatActivity {

    private ConexionSQLiteHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        ConexionSQLiteHelper dbHelper = new ConexionSQLiteHelper(this, "bd_juegomemoria", null, 1);
        db = dbHelper.getWritableDatabase();
        cargarRanking();
    }

    public void cargarRanking() {
        dbHelper.insertar("INSERT INTO RANKING(USERNAMETEXT,PUNTAJEINT) VALUES('fvidalsoto',0)", db);
    }
    public void clickRegresar(View view) {
        onBackPressed();
    }
}