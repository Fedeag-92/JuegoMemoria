package com.example.juegomemoria;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Juego extends AppCompatActivity implements View.OnClickListener {
    private int points, finalPoints, record, difficulty, hits, errors, errorsMax, turns, elapsed;
    private boolean gameOver, runningTime;
    private TextView user, pointsState, errorsState;
    private ImageView buttonBack;
    private ToggleButton buttonSound;
    private final Random random = new Random();
    private ArrayList<ImageView> cells;
    ArrayList<Integer> images = new ArrayList<>();
    ArrayList<Drawable> drawableLocation;
    final Handler handler = new Handler();
    private ImageView firstCard;
    private ImageView secondCard;
    private Bitmap firstImage;
    private Bitmap secondImage;
    private Chronometer chronometer;
    private long pauseOffset;
    private MediaPlayer mp;
    ArrayList<Integer> sounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        sounds = new ArrayList<>();
        sounds.add(R.raw.doh1);
        sounds.add(R.raw.doh2);
        sounds.add(R.raw.doh3);
        mp = new MediaPlayer();
        int nada;

        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("%s");
        chronometer.setBase(SystemClock.elapsedRealtime());
        runningTime = false;
        pauseOffset = 0;
        user = (TextView) findViewById(R.id.userNameJ);
        gameOver = false;
        pointsState = (TextView) findViewById(R.id.pointsJ);
        errorsState = (TextView) findViewById(R.id.errorsJ);
        buttonBack = (ImageView) findViewById(R.id.btnBackJ);
        buttonBack.setOnClickListener(this);

        buttonSound = (ToggleButton) findViewById(R.id.btnSoundJ);
        buttonSound.setOnClickListener(this);

        if (!MainActivity.getMediaPlayer().isPlaying())
            buttonSound.setChecked(false);

        user.setText(getIntent().getStringExtra("user"));
        this.hits = 0;
        points = 0;
        images = new ArrayList<>();
        errors = 1;
        images.addAll(Arrays.asList(R.drawable.card1, R.drawable.card2, R.drawable.card3, R.drawable.card4, R.drawable.card5, R.drawable.card6, R.drawable.card7, R.drawable.card8, R.drawable.card9, R.drawable.card10, R.drawable.card11, R.drawable.card12, R.drawable.card13, R.drawable.card14, R.drawable.card15, R.drawable.card16, R.drawable.card17, R.drawable.card18, R.drawable.card19, R.drawable.card20));
        difficulty = getIntent().getIntExtra("choice", 0);

        switch (difficulty) {
            case 1:
                for (int i = 0; i < 6; i++) {
                    ((ImageView) (findViewById(getResources().getIdentifier("error" + (i + 1), "id", getPackageName())))).setVisibility(View.INVISIBLE);
                }
                errorsState.setText("Errores: SIN LIMITES");
                turns = 6;
                errorsMax = Integer.MAX_VALUE;
                cells = new ArrayList<ImageView>(turns * 2);

                paintCells(turns * 2);

                play(turns);

                break;
            case 2:
                turns = 10;
                errorsMax = 7;
                cells = new ArrayList<ImageView>(turns * 2);
                paintCells(turns * 2);

                play(turns);
                break;
            case 3:
                for (int i = 4; i < 6; i++) {
                    ((ImageView) (findViewById(getResources().getIdentifier("error" + (i + 1), "id", getPackageName())))).setVisibility(View.INVISIBLE);
                }
                turns = 12;
                errorsMax = 5;
                cells = new ArrayList<ImageView>(turns * 2);
                paintCells(turns * 2);

                play(turns);
                break;
        }
    }

    public void paintCells(int cantCells) {
        for (int j = 0; j < 24; j++) {
            if (j < cantCells)
                addCell(j);
            else
                quitCell(j);
        }
    }

    public void startChronometer() {
        if (!runningTime) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            runningTime = true;
        }
    }

    public void pauseChronometer() {
        if (runningTime) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            runningTime = false;
        }
    }

    public void resetChronometer() {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }

    public void quitCell(int j) {
        ImageView card = searchCell(j);
        card.setVisibility(View.GONE);
    }

    public void addCell(int j) {
        ImageView card = searchCell(j);

        card.setOnClickListener(this);
        card.setVisibility(View.VISIBLE);
        card.setEnabled(false);
        cells.add(card);

    }

    public ImageView searchCell(int j) {
        ImageView card;

        String cardName = "cell" + (j + 1);
        int resIDcard = getResources().getIdentifier(cardName, "id", getPackageName());
        card = ((ImageView) findViewById(resIDcard));

        return card;
    }

    public void play(int cantImages) {
        int posCell, posImage, j;

        ArrayList<Integer> elements = new ArrayList<>();
        for (int i = 0; i < cantImages * 2; i++) {
            elements.add(i);
        }

        while (cantImages > 0) {
            j = 0;
            posImage = random.nextInt(images.size());
            while (j < 2) {
                posCell = elements.get(random.nextInt(elements.size()));
                cells.get(posCell).setImageResource(images.get(posImage));
                elements.remove(Integer.valueOf(posCell));
                j++;
            }
            images.remove(posImage);
            cantImages--;
        }

        drawableLocation = new ArrayList<>();
        for (int i = 0; i < cells.size(); i++) {
            drawableLocation.add(cells.get(i).getDrawable());
        }

        handler.postDelayed(new Runnable() {
            public void run() {
                for (int i = 0; i < cells.size(); i++) {
                    cells.get(i).setImageResource(R.drawable.dona);
                    cells.get(i).setEnabled(true);
                    startChronometer();
                }
            }
        }, 5000);   //5 seconds

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == buttonBack.getId())
            onBackPressed();
        else if (v.getId() == buttonSound.getId()) {
            if (!buttonSound.isChecked()) {
                MainActivity.getMediaPlayer().pause();
            } else {
                MainActivity.getMediaPlayer().start();
            }
        } else {
            if (!gameOver) {
                if (difficulty == 1)
                    repaintEasy(v);
                else if (difficulty == 2)
                    repaintNormal(v);
                else
                    repaintHard(v);

                if (firstCard == null) {
                    firstCard = (ImageView) findViewById(v.getId());
                    firstImage = ((BitmapDrawable) (firstCard).getDrawable()).getBitmap();
                    firstCard.setEnabled(false);
                } else {
                    secondCard = (ImageView) findViewById(v.getId());
                    secondImage = ((BitmapDrawable) (secondCard).getDrawable()).getBitmap();
                }
                if (firstCard != null && secondCard != null) {
                    for (int i = 0; i < cells.size(); i++) {
                        if (cells.get(i).getVisibility() == View.VISIBLE)
                            cells.get(i).setEnabled(false);
                    }
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            if (firstImage.sameAs(secondImage)) {
                                turns--;
                                hits++;
                                if (turns <= 0) {
                                    gameOver = true;
                                    endGame();
                                }
                                firstCard.setVisibility(View.INVISIBLE);
                                secondCard.setVisibility(View.INVISIBLE);
                            } else {
                                AssetFileDescriptor afd = getResources().openRawResourceFd(sounds.get((int)(Math.random()*4)));
                                try {
                                    mp.reset();
                                    mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
                                    mp.prepare();
                                    mp.start();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                errors++;
                                if (errors >= errorsMax) {
                                    gameOver = true;
                                    endGame();
                                }
                                Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_LONG).show();
                                if (difficulty == 2 || difficulty == 3) {
                                    ImageView errorImg;
                                    ((ImageView) (findViewById(getResources().getIdentifier("error" + (errors - 1), "id", getPackageName())))).setColorFilter(R.color.errorEnable);
                                    Toast.makeText(getApplicationContext(), (errorsMax - errors) + " errores mas y pierdes", Toast.LENGTH_LONG).show();
                                }
                                firstCard.setImageResource(R.drawable.dona);
                                secondCard.setImageResource(R.drawable.dona);
                            }
                            points = (hits * 100) / (errors);
                            pointsState.setText("Puntaje: " + points);
                            for (int i = 0; i < cells.size(); i++) {
                                if (cells.get(i).getVisibility() == View.VISIBLE)
                                    cells.get(i).setEnabled(true);
                            }
                            firstCard = null;
                            secondCard = null;
                            firstImage = null;
                            secondImage = null;
                        }

                    }, 1000);
                }
            } else
                Toast.makeText(getApplicationContext(), "Juego terminado, empiece uno nuevo yendo hacia atras", Toast.LENGTH_LONG).show();

        }

    }

    public void endGame() {
        pauseChronometer();
        elapsed = (int) (SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000;
        calculateFinalPoints(elapsed);
        Toast.makeText(getApplicationContext(), "Tiempo: " + elapsed + " segundos.", Toast.LENGTH_LONG).show();
        Intent i = new Intent(Juego.this, FinJuego.class);
        i.putExtra("user", this.user.getText());
        i.putExtra("points", this.finalPoints);
        i.putExtra("difficulty", this.difficulty);
        i.putExtra("errors", this.errors);
        i.putExtra("time", elapsed);
        finish();
        startActivity(i);
    }

    public void calculateFinalPoints(int seconds) {
        finalPoints = ((hits * 100) / (errors + elapsed)) * 10;
    }

    public int randomSound(){
        int rSound = (int)(Math.random()*3);
        return (sounds.get(rSound));
    }

    public void repaintEasy(View v) {
        switch (v.getId()) {
            case R.id.cell1:
                cells.get(0).setImageDrawable(drawableLocation.get(0));
                break;
            case R.id.cell2:
                cells.get(1).setImageDrawable(drawableLocation.get(1));
                break;
            case R.id.cell3:
                cells.get(2).setImageDrawable(drawableLocation.get(2));
                break;
            case R.id.cell4:
                cells.get(3).setImageDrawable(drawableLocation.get(3));
                break;
            case R.id.cell5:
                cells.get(4).setImageDrawable(drawableLocation.get(4));
                break;
            case R.id.cell6:
                cells.get(5).setImageDrawable(drawableLocation.get(5));
                break;
            case R.id.cell7:
                cells.get(6).setImageDrawable(drawableLocation.get(6));
                break;
            case R.id.cell8:
                cells.get(7).setImageDrawable(drawableLocation.get(7));
                break;
            case R.id.cell9:
                cells.get(8).setImageDrawable(drawableLocation.get(8));
                break;
            case R.id.cell10:
                cells.get(9).setImageDrawable(drawableLocation.get(9));
                break;
            case R.id.cell11:
                cells.get(10).setImageDrawable(drawableLocation.get(10));
                break;
            case R.id.cell12:
                cells.get(11).setImageDrawable(drawableLocation.get(11));
                break;
        }
    }

    public void repaintNormal(View v) {
        switch (v.getId()) {
            case R.id.cell1:
                cells.get(0).setImageDrawable(drawableLocation.get(0));
                break;
            case R.id.cell2:
                cells.get(1).setImageDrawable(drawableLocation.get(1));
                break;
            case R.id.cell3:
                cells.get(2).setImageDrawable(drawableLocation.get(2));
                break;
            case R.id.cell4:
                cells.get(3).setImageDrawable(drawableLocation.get(3));
                break;
            case R.id.cell5:
                cells.get(4).setImageDrawable(drawableLocation.get(4));
                break;
            case R.id.cell6:
                cells.get(5).setImageDrawable(drawableLocation.get(5));
                break;
            case R.id.cell7:
                cells.get(6).setImageDrawable(drawableLocation.get(6));
                break;
            case R.id.cell8:
                cells.get(7).setImageDrawable(drawableLocation.get(7));
                break;
            case R.id.cell9:
                cells.get(8).setImageDrawable(drawableLocation.get(8));
                break;
            case R.id.cell10:
                cells.get(9).setImageDrawable(drawableLocation.get(9));
                break;
            case R.id.cell11:
                cells.get(10).setImageDrawable(drawableLocation.get(10));
                break;
            case R.id.cell12:
                cells.get(11).setImageDrawable(drawableLocation.get(11));
                break;
            case R.id.cell13:
                cells.get(12).setImageDrawable(drawableLocation.get(12));
                break;
            case R.id.cell14:
                cells.get(13).setImageDrawable(drawableLocation.get(13));
                break;
            case R.id.cell15:
                cells.get(14).setImageDrawable(drawableLocation.get(14));
                break;
            case R.id.cell16:
                cells.get(15).setImageDrawable(drawableLocation.get(15));
                break;
            case R.id.cell17:
                cells.get(16).setImageDrawable(drawableLocation.get(16));
                break;
            case R.id.cell18:
                cells.get(17).setImageDrawable(drawableLocation.get(17));
                break;
            case R.id.cell19:
                cells.get(18).setImageDrawable(drawableLocation.get(18));
                break;
            case R.id.cell20:
                cells.get(19).setImageDrawable(drawableLocation.get(19));
                break;
        }
    }

    public void repaintHard(View v) {
        switch (v.getId()) {
            case R.id.cell1:
                cells.get(0).setImageDrawable(drawableLocation.get(0));
                break;
            case R.id.cell2:
                cells.get(1).setImageDrawable(drawableLocation.get(1));
                break;
            case R.id.cell3:
                cells.get(2).setImageDrawable(drawableLocation.get(2));
                break;
            case R.id.cell4:
                cells.get(3).setImageDrawable(drawableLocation.get(3));
                break;
            case R.id.cell5:
                cells.get(4).setImageDrawable(drawableLocation.get(4));
                break;
            case R.id.cell6:
                cells.get(5).setImageDrawable(drawableLocation.get(5));
                break;
            case R.id.cell7:
                cells.get(6).setImageDrawable(drawableLocation.get(6));
                break;
            case R.id.cell8:
                cells.get(7).setImageDrawable(drawableLocation.get(7));
                break;
            case R.id.cell9:
                cells.get(8).setImageDrawable(drawableLocation.get(8));
                break;
            case R.id.cell10:
                cells.get(9).setImageDrawable(drawableLocation.get(9));
                break;
            case R.id.cell11:
                cells.get(10).setImageDrawable(drawableLocation.get(10));
                break;
            case R.id.cell12:
                cells.get(11).setImageDrawable(drawableLocation.get(11));
                break;
            case R.id.cell13:
                cells.get(12).setImageDrawable(drawableLocation.get(12));
                break;
            case R.id.cell14:
                cells.get(13).setImageDrawable(drawableLocation.get(13));
                break;
            case R.id.cell15:
                cells.get(14).setImageDrawable(drawableLocation.get(14));
                break;
            case R.id.cell16:
                cells.get(15).setImageDrawable(drawableLocation.get(15));
                break;
            case R.id.cell17:
                cells.get(16).setImageDrawable(drawableLocation.get(16));
                break;
            case R.id.cell18:
                cells.get(17).setImageDrawable(drawableLocation.get(17));
                break;
            case R.id.cell19:
                cells.get(18).setImageDrawable(drawableLocation.get(18));
                break;
            case R.id.cell20:
                cells.get(19).setImageDrawable(drawableLocation.get(19));
                break;
            case R.id.cell21:
                cells.get(20).setImageDrawable(drawableLocation.get(20));
                break;
            case R.id.cell22:
                cells.get(21).setImageDrawable(drawableLocation.get(21));
                break;
            case R.id.cell23:
                cells.get(22).setImageDrawable(drawableLocation.get(22));
                break;
            case R.id.cell24:
                cells.get(23).setImageDrawable(drawableLocation.get(23));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Juego.this);
        builder.setMessage("Si sales se perderá el progreso.");
        builder.setCancelable(true);
        builder.setNegativeButton("Continuar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}