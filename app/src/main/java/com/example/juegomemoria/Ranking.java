package com.example.juegomemoria;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        listadousuarios = (ListView) findViewById(R.id.listadousuarios);

        ConexionSQLiteHelper dbHelper = new ConexionSQLiteHelper(this, "bd_juegomemoria", null, 1);
        db = dbHelper.getWritableDatabase();
        cargarRanking();

        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listaInformacion);
        listadousuarios.setAdapter(adaptador);
    }

    public void cargarRanking() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        IRanking ranking = null;
        listaRanking =new ArrayList<IRanking>();

        Cursor c = db.rawQuery("SELECT * FROM ranking", null);

        while(c.moveToNext()){
            ranking = new IRanking("",0);
            ranking.setUsername(c.getString(0));
            ranking.setPuntaje(c.getInt(1));
            listaRanking.add(ranking);
        }
        obtenerLista();
    }

    public void obtenerLista(){
        listaInformacion=new ArrayList<String>();

        for (int i = 0; i < listaRanking.size(); i++) {
            listaInformacion.add(listaRanking.get(i).getUsername()+" - "+listaRanking.get(i).getPuntaje());
        }
    }
    public void clickRegresar(View view) {
        onBackPressed();
    }
}