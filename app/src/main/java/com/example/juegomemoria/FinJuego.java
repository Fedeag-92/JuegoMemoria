package com.example.juegomemoria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

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
    ArrayList<Integer> winSounds;
    ArrayList<Integer> imgLose;
    ArrayList<Integer> loseSounds;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin_juego);
        conectarBD();

        mp = new MediaPlayer();

        imgWin = new ArrayList<Integer>();
        imgWin.add(R.drawable.bartwin);
        imgWin.add(R.drawable.homerwin);
        imgWin.add(R.drawable.homerwin2);

        imgLose = new ArrayList<Integer>();
        imgLose.add(R.drawable.lisaloser);
        imgLose.add(R.drawable.homerloser);
        imgLose.add(R.drawable.nelsonloser);

        winSounds = new ArrayList<Integer>();
        loseSounds = new ArrayList<Integer>();
        winSounds.add(R.raw.yajuhomero);
        winSounds.add(R.raw.neerd);
        winSounds.add(R.raw.volverchango);
        loseSounds.add(R.raw.idiota);
        loseSounds.add(R.raw.chan);
        loseSounds.add(R.raw.nelsonaha);

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
            MainActivity.getMediaPlayer().pause();
            playSound(winSounds.get((int)(Math.random()*winSounds.size())));

            ((ImageView)findViewById(R.id.imgResult)).setImageResource(imgWin.get(random));
            ((ImageView) findViewById(R.id.checkRecordOn)).setVisibility(View.VISIBLE);
            ((ImageView) findViewById(R.id.checkRecordOff)).setVisibility(View.INVISIBLE);
        } else {
            MainActivity.getMediaPlayer().pause();
            playSound(loseSounds.get((int)(Math.random()*loseSounds.size())));

            ((ImageView)findViewById(R.id.imgResult)).setImageResource(imgLose.get(random));
            ((ImageView) findViewById(R.id.checkRecordOn)).setVisibility(View.INVISIBLE);
            ((ImageView) findViewById(R.id.checkRecordOff)).setVisibility(View.VISIBLE);
        }
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                playSound(R.raw.ending);
            }

        });

    }


    public void playSound(int song) {
        AssetFileDescriptor afd = getResources().openRawResourceFd(song);
        try {
            mp.reset();
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
            mp.prepare();
            mp.start();
            afd.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void conectarBD() {
        dbHelper = new ConexionSQLiteHelper(this, "bd_juegomemoria", null, 1);
        db = dbHelper.getWritableDatabase();
        dbr = dbHelper.getReadableDatabase();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        mp.stop();
        MainActivity.getMediaPlayer().seekTo(0);
        MainActivity.getMediaPlayer().start();
    }
}