package com.example.juegomemoria;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
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
    private int points, finalPoints, difficulty, hits, errors, errorsMax, turns, elapsed;
    private boolean gameOver, runningTime;
    private TextView user, pointsState, errorsState;
    private ImageView buttonBack;
    private ToggleButton buttonSound;
    private final Random random = new Random();
    private ArrayList<ImageView> cells;
    ArrayList<Integer> images = new ArrayList<>();
    final Handler handler = new Handler();
    private ImageView firstCard;
    private ImageView secondCard;
    private int firstImage;
    private int secondImage;
    private Chronometer chronometer;
    private long pauseOffset;
    private MediaPlayer mp;
    ArrayList<Integer> errorSounds;
    ArrayList<Integer> hitSounds;
    StateListDrawable d;
    Bitmap bitmap1, bitmap2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        errorSounds = new ArrayList<>();
        hitSounds = new ArrayList<>();

        errorSounds.add(R.raw.doh1);
        errorSounds.add(R.raw.doh2);
        errorSounds.add(R.raw.doh3);
        hitSounds.add(R.raw.matanga);
        hitSounds.add(R.raw.yajuhomero2);

        mp = new MediaPlayer();

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
                turns = 8;
                errorsMax = 7;
                cells = new ArrayList<ImageView>(turns * 2);
                paintCells(turns * 2);

                play(turns);
                break;
            case 3:
                for (int i = 4; i < 6; i++) {
                    ((ImageView) (findViewById(getResources().getIdentifier("error" + (i + 1), "id", getPackageName())))).setVisibility(View.INVISIBLE);
                }
                turns = 10;
                errorsMax = 5;
                cells = new ArrayList<ImageView>(turns * 2);
                paintCells(turns * 2);

                play(turns);
                break;
        }
    }

    public void paintCells(int cantCells) {
        for (int j = 0; j < 20; j++) {
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
        card.setSelected(true);
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

                d = new StateListDrawable();

                int[] notShow = {android.R.attr.state_enabled, -android.R.attr.state_selected};
                int[] notShowToo = {-android.R.attr.state_enabled, -android.R.attr.state_selected};
                int[] show = {-android.R.attr.state_enabled, android.R.attr.state_selected};
                Drawable drawNotShow = getDrawable(R.drawable.dona);
                Drawable drawShow = getDrawable(images.get(posImage));
                d.addState(notShowToo, drawNotShow);
                d.addState(show, drawShow);
                d.addState(notShow, drawNotShow);
                cells.get(posCell).setImageDrawable(d);
                cells.get(posCell).setTag(images.get(posImage));
                elements.remove(Integer.valueOf(posCell));
                j++;
            }
            images.remove(posImage);
            cantImages--;
        }

        handler.postDelayed(new Runnable() {
            public void run() {
                for (int i = 0; i < cells.size(); i++) {
                    cells.get(i).setEnabled(true);
                    cells.get(i).setSelected(false);
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
                if (firstCard == null) {
                    firstCard = (ImageView) findViewById(v.getId());
                    firstImage = (int) firstCard.getTag();
                    firstCard.setEnabled(false);
                    firstCard.setSelected(true);
                } else {
                    secondCard = (ImageView) findViewById(v.getId());
                    secondImage = (int) secondCard.getTag();
                    secondCard.setEnabled(false);
                    secondCard.setSelected(true);
                }
                if (firstCard != null && secondCard != null) {
                    for (int i = 0; i < cells.size(); i++) {
                        if (cells.get(i).getVisibility() == View.VISIBLE)
                            cells.get(i).setEnabled(false);
                    }
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            if (firstImage == secondImage) {
                                playSound(hitSounds);
                                turns--;
                                hits++;
                                if (turns <= 0) {
                                    gameOver = true;
                                    endGame();
                                }
                                firstCard.setVisibility(View.INVISIBLE);
                                secondCard.setVisibility(View.INVISIBLE);
                            } else {
                                playSound(errorSounds);
                                errors++;
                                if (errors >= errorsMax) {
                                    gameOver = true;
                                    endGame();
                                }
                                if (difficulty == 2 || difficulty == 3) {
                                    ImageView errorImg;
                                    ((ImageView) (findViewById(getResources().getIdentifier("error" + (errors - 1), "id", getPackageName())))).setColorFilter(R.color.errorEnable);
                                }
                            }
                            points = (hits * 100) / (errors);
                            pointsState.setText("Puntaje: " + points);
                            for (int i = 0; i < cells.size(); i++) {
                                if (cells.get(i).getVisibility() == View.VISIBLE){
                                    cells.get(i).setEnabled(true);
                                    cells.get(i).setSelected(false);
                                }


                            }
                            firstCard = null;
                            secondCard = null;
                            firstImage = 0;
                            secondImage = 0;
                        }

                    }, 700);
                }
            }

        }

    }



    public void playSound(ArrayList<Integer> sounds) {
        AssetFileDescriptor afd = getResources().openRawResourceFd(sounds.get((int) (Math.random() * (sounds.size()))));
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

    public void endGame() {
        pauseChronometer();
        elapsed = (int) (SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000;
        calculateFinalPoints(elapsed);
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

    public int randomSound() {
        int rSound = (int) (Math.random() * 3);
        return (errorSounds.get(rSound));
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