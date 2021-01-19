package com.example.juegomemoria;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FinJuego extends AppCompatActivity {
    private int points, difficulty, errors, time;
    private boolean isRecord;
    private String user;
    private static final String TAG = "MyActivity";
    private TextView pointsV, errorsV, difficultyV;
    private ImageView recOn, recOff;
    private ConexionSQLiteHelper dbHelper;
    private SQLiteDatabase db, dbr;
    ArrayList<Integer> imgWin;
    ArrayList<Integer> imgLose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin_juego);
        conectarBD();

        imgWin = new ArrayList<Integer>();
        imgWin.add(R.drawable.bartwin);
        imgWin.add(R.drawable.homerwin);
        imgWin.add(R.drawable.homerwin2);

        imgLose = new ArrayList<Integer>();
        imgLose.add(R.drawable.lisaloser);
        imgLose.add(R.drawable.homerloser);
        imgLose.add(R.drawable.nelsonloser);

        user = getIntent().getStringExtra("user");
        points = getIntent().getIntExtra("points", 0);
        isRecord = getIntent().getBooleanExtra("record", false);
        difficulty = getIntent().getIntExtra("difficulty", 0);
        errors = getIntent().getIntExtra("errors", 0);
        time = getIntent().getIntExtra("time", 0);
        ((TextView) findViewById(R.id.pointsEnd)).setText("Puntaje: " + points);
        ((TextView) findViewById(R.id.errorsEnd)).setText("Errores: " + errors);
        String dif;
        if (difficulty == 1) {
            dif = "Facil";
        } else if (difficulty == 2) {
            dif = "Normal";
        } else {
            dif = "Dificil";
        }
        Cursor c = dbr.rawQuery("SELECT * FROM RANKING WHERE username= '" + user + "'", null);
        if (!c.moveToFirst()){
            dbHelper.insertar("INSERT INTO RANKING(USERNAME,PUNTAJE,DIFICULTAD) VALUES('" + user + "','" + points + "','" + dif + "')", db);
            isRecord = true;
        }

        else if (Integer.parseInt(c.getString(2)) < points){
            db.execSQL("UPDATE RANKING SET PUNTAJE='" + points + "', DIFICULTAD='"+dif+"' WHERE USERNAME='" + user + "'");
            isRecord = true;
        }

        ((TextView) findViewById(R.id.difficultyRanking)).setText("Dificultad: " + dif);
        String timeString = "";
        if (time > 60) {
            timeString = timeString + time / 60 + ":" + time % 60;
            ((TextView) findViewById(R.id.timeEnd)).setText("Tiempo: " + timeString);
        } else
            ((TextView) findViewById(R.id.timeEnd)).setText("Tiempo: 00:" + time);

        ((TextView) findViewById(R.id.userNameEnd)).setText(user);
        int random = (int)(Math.random()*3);
        if (isRecord) {
            ((ImageView)findViewById(R.id.imgResult)).setImageResource(imgWin.get(random));
            ((ImageView) findViewById(R.id.checkRecordOn)).setVisibility(View.VISIBLE);
            ((ImageView) findViewById(R.id.checkRecordOff)).setVisibility(View.INVISIBLE);
        } else {
            ((ImageView)findViewById(R.id.imgResult)).setImageResource(imgLose.get(random));
            ((ImageView) findViewById(R.id.checkRecordOn)).setVisibility(View.INVISIBLE);
            ((ImageView) findViewById(R.id.checkRecordOff)).setVisibility(View.VISIBLE);
        }
    }

    public void conectarBD() {
        dbHelper = new ConexionSQLiteHelper(this, "bd_juegomemoria", null, 1);
        db = dbHelper.getWritableDatabase();
        dbr = dbHelper.getReadableDatabase();
    }
}