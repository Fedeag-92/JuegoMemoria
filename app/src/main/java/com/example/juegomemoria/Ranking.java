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
        dbHelper = new ConexionSQLiteHelper(this, "bd_juegomemoria", null, 1);
        db = dbHelper.getWritableDatabase();
        cargarRanking();

    }

    public void clickRegresar(View view) {
        onBackPressed();
    }

    public void cargarRanking(){
        dbHelper.insertar("INSERT INTO RANKING VALUES('1','fvidalsoto','1432')", db);
        dbHelper.close();
        //dbHelper.insertar("INSERT INTO RANKING(USERNAME_RANKING,PUNTAJE) VALUES('fvidalsoto','1432')", db);
        //dbHelper.insertar("INSERT INTO RANKING(USERNAME_RANKING,PUNTAJE) VALUES('mpalavecino','2461')", db);
        //dbHelper.insertar("INSERT INTO RANKING(USERNAME_RANKING,PUNTAJE) VALUES('fmaguilera','1')", db);
        //dbHelper.insertar("INSERT INTO RANKING(USERNAME_RANKING,PUNTAJE) VALUES('patricioenriquesebastian','653')", db);
    }

}