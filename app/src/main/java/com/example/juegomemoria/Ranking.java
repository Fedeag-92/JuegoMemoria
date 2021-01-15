package com.example.juegomemoria;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.juegomemoria.entidades.IRanking;
import java.util.ArrayList;

public class Ranking extends AppCompatActivity {

    private ConexionSQLiteHelper dbHelper;
    private SQLiteDatabase db;

    ListView listadousuarios;
    ArrayList<String> listaInformacion;
    ArrayList<IRanking> listaRanking;
    private static final String TAG = "MyActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);


        listadousuarios = (ListView) findViewById(R.id.listadousuarios);

        dbHelper = new ConexionSQLiteHelper(this, "bd_juegomemoria", null, 1);
        db = dbHelper.getWritableDatabase();

        cargarRanking();

        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listaInformacion);
        listadousuarios.setAdapter(adaptador);
    }

    public void cargarRanking() {
        SQLiteDatabase dbr = dbHelper.getReadableDatabase();
        IRanking ranking = null;
        listaRanking =new ArrayList<IRanking>();
        /*dbHelper.insertar("INSERT INTO RANKING(USERNAME,PUNTAJE) VALUES('mpalavecino','1245')", db);
        dbHelper.insertar("INSERT INTO RANKING(USERNAME,PUNTAJE) VALUES('fedeaguilera','1652')", db);
        dbHelper.insertar("INSERT INTO RANKING(USERNAME,PUNTAJE) VALUES('fvidalsoto','653')", db);
        dbHelper.insertar("INSERT INTO RANKING(USERNAME,PUNTAJE) VALUES('mvillalba','357')", db);*/
        Cursor c = dbr.rawQuery("SELECT * FROM ranking", null);
        Log.i(TAG,"Cursor es: "+c+" con resultado: "+c.moveToFirst());
        for (int i = 0; i < c.getCount(); i++) {
            ranking = new IRanking("",0);
            ranking.setUsername(c.getString(1));
            ranking.setPuntaje(c.getInt(2));
            listaRanking.add(ranking);
            c.moveToNext();
        }
        obtenerLista();
    }

    public void obtenerLista(){
        listaInformacion=new ArrayList<String>();

        for (int i = 0; i < listaRanking.size(); i++) {
            Log.i(TAG,"USUARIO"+listaRanking.get(i).getUsername());
            listaInformacion.add("Username: "+listaRanking.get(i).getUsername()+" | Puntaje: "+listaRanking.get(i).getPuntaje());
        }
    }
    public void clickRegresar(View view) {
        onBackPressed();
    }
}