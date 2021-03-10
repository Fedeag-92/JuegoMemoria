package com.example.juegomemoria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class FinJuego extends AppCompatActivity {
    private ConexionSQLiteHelper dbHelper;
    private SQLiteDatabase db, dbr;
    private ArrayList<Integer> imgWin;
    private ArrayList<Integer> winSounds;
    private ArrayList<Integer> imgLose;
    private ArrayList<Integer> imagesGameOver;
    private boolean lostGame;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin_juego);
        conectarBD();

        TextView tittleEndGame = (TextView) findViewById(R.id.endGameText);
        tittleEndGame.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/simpson.ttf"));

        mp = new MediaPlayer();

        lostGame = getIntent().getBooleanExtra("lostGame",false);
        String user = getIntent().getStringExtra("user");
        ((TextView) findViewById(R.id.userNameEnd)).setText(user);

        if(!lostGame) {
            showResult();
            imgWin = new ArrayList<Integer>();
            imgWin.add(R.drawable.bartwin);
            imgWin.add(R.drawable.homerwin);
            imgWin.add(R.drawable.homerwin2);

            imgLose = new ArrayList<Integer>();
            imgLose.add(R.drawable.lisaloser);
            imgLose.add(R.drawable.homerloser);
            imgLose.add(R.drawable.nelsonloser);

            winSounds = new ArrayList<Integer>();

            winSounds.add(R.raw.yajuhomero);
            winSounds.add(R.raw.neerd);
            winSounds.add(R.raw.volverchango);

            int points = getIntent().getIntExtra("points", 0);
            boolean isRecord = getIntent().getBooleanExtra("record", false);
            int difficulty = getIntent().getIntExtra("difficulty", 0);
            int errors = getIntent().getIntExtra("errors", 0);
            int time = getIntent().getIntExtra("time", 0);
            String dif;

            if (difficulty == 1) {
                dif = "Facil";
            } else if (difficulty == 2) {
                dif = "Normal";
            } else {
                dif = "Dificil";
            }
            ((TextView) findViewById(R.id.pointsEnd)).setText("Puntaje: " + points);
            ((TextView) findViewById(R.id.errorsEnd)).setText("Errores: " + (--errors));
            ((TextView) findViewById(R.id.difficultyRanking)).setText("Dificultad: " + dif);
            String timeString = "";
            if (time > 60) {
                if(time % 60 < 10)
                    timeString = timeString + time / 60 + ":0" + time % 60;
                else
                    timeString = timeString + time / 60 + ":" + time % 60;
                ((TextView) findViewById(R.id.timeEnd)).setText("Tiempo: " + timeString);
            } else
                ((TextView) findViewById(R.id.timeEnd)).setText("Tiempo: 00:" + time);

            Cursor c = dbr.rawQuery("SELECT * FROM RANKING WHERE username= '" + user + "'", null);
            if (!c.moveToFirst()) {
                dbHelper.insertar("INSERT INTO RANKING(USERNAME,PUNTAJE,DIFICULTAD) VALUES('" + user + "','" + points + "','" + dif + "')", db);
                isRecord = true;
            } else if (Integer.parseInt(c.getString(2)) < points) {
                db.execSQL("UPDATE RANKING SET PUNTAJE='" + points + "', DIFICULTAD='" + dif + "' WHERE USERNAME='" + user + "'");
                isRecord = true;
            }

            int random = (int) (Math.random() * 3);
            if (isRecord) {
                ((TextView) findViewById(R.id.recordText)).setText(R.string.record);
                MainActivity.getMediaPlayer().pause();
                playSound(winSounds.get((int) (Math.random() * winSounds.size())));

                ((ImageView) findViewById(R.id.imgResult)).setImageResource(imgWin.get(random));
            } else {
                ((TextView) findViewById(R.id.recordText)).setText(R.string.norecord);
                ((ImageView) findViewById(R.id.imgResult)).setImageResource(imgLose.get(random));
            }
        }
        else{
            int random = (int) (Math.random() * 5);
            playSound(R.raw.chan);
            mostrarImgError(random);
        }

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.setVolume(0.07f, 0.07f);
                playSound(R.raw.ending);
            }
        });
    }

    public void mostrarImgError(int random){
        imagesGameOver = new ArrayList<Integer>();
        imagesGameOver.add(R.drawable.gameover1);
        imagesGameOver.add(R.drawable.gameover2);
        imagesGameOver.add(R.drawable.gameover3);
        imagesGameOver.add(R.drawable.gameover4);
        imagesGameOver.add(R.drawable.gameover5);
        ((ImageView) findViewById(R.id.gameOverImage)).setImageResource(imagesGameOver.get(random));
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

    public void stopSound(){
        mp.stop();
        mp.release();
        mp = null;
    }

    public void conectarBD() {
        dbHelper = new ConexionSQLiteHelper(this, "bd_juegomemoria", null, 1);
        db = dbHelper.getWritableDatabase();
        dbr = dbHelper.getReadableDatabase();
    }

    @Override
    public void onBackPressed() {
        this.stopSound();
        super.onBackPressed();
        MainActivity.getMediaPlayer().seekTo(0);
        MainActivity.getMediaPlayer().start();
    }

    public void clickRegresar(View v) {
        this.onBackPressed();
    }

    public void showResult(){
        findViewById(R.id.pointsEnd).setVisibility(View.VISIBLE);
        findViewById(R.id.errorsEnd).setVisibility(View.VISIBLE);
        findViewById(R.id.difficultyRanking).setVisibility(View.VISIBLE);
        findViewById(R.id.timeEnd).setVisibility(View.VISIBLE);
        findViewById(R.id.whiteTransparentBack).setVisibility(View.VISIBLE);
        findViewById(R.id.imgResult).setVisibility(View.VISIBLE);
        findViewById(R.id.recordText).setVisibility(View.VISIBLE);
        findViewById(R.id.gameOverImage).setVisibility(View.GONE);
        findViewById(R.id.txtGameOver).setVisibility(View.INVISIBLE);
    }
}