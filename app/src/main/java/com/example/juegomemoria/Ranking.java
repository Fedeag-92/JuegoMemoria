package com.example.juegomemoria;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.juegomemoria.entidades.PuntajeRanking;

import java.util.ArrayList;

public class Ranking extends AppCompatActivity {
    private ConexionSQLiteHelper dbHelper;
    private SQLiteDatabase db, dbr;
    TableLayout tablaRanking;
    ArrayList<PuntajeRanking> listaRanking;
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        tablaRanking = (TableLayout) findViewById(R.id.listaRanking);

        conectarBD();
        cargarRanking();
        cerrarConexion();
    }

    public void clickRegresar(View view) {
        onBackPressed();
    }

    @SuppressLint("ResourceType")
    public void cargarRanking() {

        Cursor c = dbr.rawQuery("SELECT * FROM RANKING ORDER BY PUNTAJE DESC", null);
        c.moveToFirst();

        for (int i = 0; i < c.getCount(); i++) {
            TableRow row = new TableRow(getBaseContext());
            TextView textView;

            //Seteo de margen la fila.
            TableLayout.LayoutParams tableRowParams =
                    new TableLayout.LayoutParams
                            (TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);

            tableRowParams.setMargins(0, 3, 0, 3);
            row.setLayoutParams(tableRowParams);

            for (int j = 0; j < 3; j++) {
                textView = new TextView(getBaseContext());
                textView.setText(c.getString(j + 1));
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setPadding(15, 15, 15, 15);
                textView.setBackgroundColor(Color.parseColor("#E1FF6E40"));
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                row.addView(textView);
            }
            tablaRanking.addView(row);
            c.moveToNext();
        }
    }

    public void conectarBD() {
        dbHelper = new ConexionSQLiteHelper(this, "bd_juegomemoria", null, 1);
        //db es para insertar los datos estaticos en la BD. Pruebas.
        //db = dbHelper.getWritableDatabase();
        dbr = dbHelper.getReadableDatabase();
    }

    public void cerrarConexion() {
        dbHelper.close();
    }

}