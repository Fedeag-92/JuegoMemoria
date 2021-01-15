package com.example.juegomemoria;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.juegomemoria.entidades.PuntajeRanking;

import java.util.ArrayList;

import static android.graphics.Color.WHITE;

public class Ranking extends AppCompatActivity {
    private ConexionSQLiteHelper dbHelper;
    private SQLiteDatabase db;
    TableLayout tablaRanking;
    PuntajeRanking puntajes = null;
    ArrayList<PuntajeRanking> listaRanking;
    private static final String TAG = "MyActivity";
    TextView username,puntaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        tablaRanking = (TableLayout)findViewById(R.id.listaRanking);
        cargarRanking();

    }

    public void clickRegresar(View view) {
        onBackPressed();
    }

    @SuppressLint("ResourceType")
    public void cargarRanking(){

        conectarBD();
        SQLiteDatabase dbr = dbHelper.getReadableDatabase();

        Cursor c = dbr.rawQuery("SELECT * FROM RANKING ORDER BY PUNTAJE DESC", null);

        Log.i(TAG,"Cursor es: "+c+" con resultado: "+c.moveToFirst());

        for (int i = 0; i < c.getCount(); i++) {
            puntajes = new PuntajeRanking("",0);
            puntajes.setUsername(c.getString(1));
            puntajes.setPuntaje(c.getInt(2));

            String [] cadena = {c.getString(1), c.getString(2)};
            TableRow row = new TableRow(getBaseContext());
            TextView textView;

        Log.i(TAG,"CADENA ES "+c.getString(1)+" con resultado: "+c.getString(2));

            for (int j = 0; j < 2; j++) {
                 textView = new TextView(getBaseContext());
                 textView.setGravity(Gravity.CENTER_VERTICAL);
                 textView.setPadding(15,15,15,15);
                 textView.setBackgroundResource(R.color.design_default_color_on_primary);
                 textView.setText(cadena[j]);
                 //textView.setText(WHITE);
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                 row.addView(textView);
            }
            tablaRanking.addView(row);

            c.moveToNext();
        }

        //dbHelper.insertar("INSERT INTO RANKING VALUES('1','fvidalsoto','1432')", db);
        //dbHelper.insertar("INSERT INTO RANKING(USERNAME,PUNTAJE) VALUES('patricioenriquesebastian','653')", db);
       /* dbHelper.insertar("INSERT INTO RANKING(USERNAME,PUNTAJE) VALUES('mpalavecino','1245')", db);
        dbHelper.insertar("INSERT INTO RANKING(USERNAME,PUNTAJE) VALUES('fedeaguilera','1652')", db);
        dbHelper.insertar("INSERT INTO RANKING(USERNAME,PUNTAJE) VALUES('fvidalsoto','653')", db);
        dbHelper.insertar("INSERT INTO RANKING(USERNAME,PUNTAJE) VALUES('mvillalba','357')", db);
        */
        //db.close();


        //dbHelper.insertar("INSERT INTO RANKING(USERNAME_RANKING,PUNTAJE) VALUES('fvidalsoto','1432')", db);
        //dbHelper.insertar("INSERT INTO RANKING(USERNAME_RANKING,PUNTAJE) VALUES('mpalavecino','2461')", db);
        //dbHelper.insertar("INSERT INTO RANKING(USERNAME_RANKING,PUNTAJE) VALUES('fmaguilera','1')", db);
        //dbHelper.insertar("INSERT INTO RANKING(USERNAME_RANKING,PUNTAJE) VALUES('patricioenriquesebastian','653')", db);
    }

    public void conectarBD(){
        dbHelper = new ConexionSQLiteHelper(this, "bd_juegomemoria", null, 1);
        db = dbHelper.getWritableDatabase();
    }

}