package com.example.juegomemoria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Juego extends AppCompatActivity implements View.OnClickListener {
    private int points, finalPoints, record, difficulty, hits, errors, errorsMax, turns, elapsed;
    private boolean gameOver, runningTime;
    private TextView user, pointsState, errorsState, time;
    private ImageView buttonBack;
    private ToggleButton buttonSound;
    private AudioManager amanager;
    private final Random random = new Random();
    private ArrayList<ImageView> cards;
    ArrayList<Integer> images = new ArrayList<>();
    ArrayList<Integer> usedImages;
    ArrayList<Drawable> drawableLocation;
    final Handler handler = new Handler();
    private ImageView firstCard;
    private ImageView secondCard;
    private Bitmap firstImage;
    private Bitmap secondImage;
    private Chronometer chronometer;
    private long pauseOffset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("%s");
        chronometer.setBase(SystemClock.elapsedRealtime());
        runningTime = false;
        pauseOffset = 0;
        time = (TextView) findViewById(R.id.timeJ);
        user = (TextView) findViewById(R.id.userNameJ);
        gameOver = false;
        pointsState = (TextView) findViewById(R.id.pointsJ);
        errorsState = (TextView) findViewById(R.id.errorsJ);
        buttonBack = (ImageView) findViewById(R.id.btnBackJ);
        buttonBack.setOnClickListener(this);
        buttonSound = (ToggleButton) findViewById(R.id.btnSoundJ);
        buttonSound.setOnClickListener(this);

        amanager = (AudioManager) getSystemService(AUDIO_SERVICE);

        user.setText(getIntent().getStringExtra("user"));
        this.hits = 0;
        points = 0;
        images = new ArrayList<>();
        errors = 1;
        images.addAll(Arrays.asList(R.drawable.card1, R.drawable.card2, R.drawable.card3, R.drawable.card4, R.drawable.card5, R.drawable.card6, R.drawable.card7, R.drawable.card8, R.drawable.card9, R.drawable.card10, R.drawable.card11, R.drawable.card12, R.drawable.card13, R.drawable.card14, R.drawable.card15, R.drawable.card16, R.drawable.card17, R.drawable.card18, R.drawable.card19, R.drawable.card20));
        usedImages = new ArrayList<>(images);
        difficulty = getIntent().getIntExtra("choice", 0);

        switch (difficulty) {
            case 1:
                for (int i = 0; i < 6; i++) {
                    ((ImageView) (findViewById(getResources().getIdentifier("error" + (i + 1), "id", getPackageName())))).setVisibility(View.INVISIBLE);
                }
                time.setTranslationX(-100);
                chronometer.setTranslationX(-100);
                errorsState.setText("Errores: SIN LIMITES");
                turns = 6;
                errorsMax = Integer.MAX_VALUE;
                cards = new ArrayList<ImageView>(12);
                for (int j = 0; j < 40; j++) {
                    if (j != 4 && j != 9 && j != 14 && (j < 15)) {
                        addCard(j);
                    } else
                        quitCard(j);
                }

                for (int i = 0; i < images.size() - turns; i++) {
                    usedImages.remove(random.nextInt(Integer.valueOf(usedImages.size())));
                }
                play(turns);

                break;
            case 2:
                turns = 12;
                errorsMax = 7;
                cards = new ArrayList<ImageView>(24);
                for (int j = 0; j < 29; j++) {
                    if (j != 4 && j != 9 && j != 14 && j != 19 && j != 24) {
                        addCard(j);
                    }
                }

                for (int i = 0; i < images.size() - turns; i++) {
                    usedImages.remove(random.nextInt(Integer.valueOf(usedImages.size())));
                }

                play(turns);
                break;
            case 3:
                for (int i = 3; i < 6; i++) {
                    ((ImageView) (findViewById(getResources().getIdentifier("error" + (i + 1), "id", getPackageName())))).setVisibility(View.INVISIBLE);
                }
                turns = 20;
                errorsMax = 4;
                cards = new ArrayList<ImageView>(40);
                for (int j = 0; j < 40; j++) {
                    addCard(j);
                }

                play(turns);
                break;
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

    public void quitCard(int j) {
        ImageView card = searchCard(j);
        card.setVisibility(View.GONE);
    }

    public void addCard(int j) {
        ImageView card = searchCard(j);

        card.setOnClickListener(this);
        card.setVisibility(View.VISIBLE);
        card.setEnabled(false);
        cards.add(card);

        if (difficulty != 3 && (j == 0 || j == 5 || j == 10 || j == 15 || j == 20 || j == 25)) {
            ConstraintLayout.LayoutParams parameter = (ConstraintLayout.LayoutParams) card.getLayoutParams();
            parameter.setMarginStart(150);
            card.setLayoutParams(parameter);
        }
    }

    public ImageView searchCard(int j) {
        ImageView card;

        String cardName = "card" + (j + 1);
        int resIDcard = getResources().getIdentifier(cardName, "id", getPackageName());
        card = ((ImageView) findViewById(resIDcard));

        return card;
    }

    public void play(int cantImages) {
        int posCeld, posImage, j;

        ArrayList<Integer> elements = new ArrayList<>();
        for (int i = 0; i < cantImages * 2; i++) {
            elements.add(i);
        }

        while (cantImages > 0) {
            j = 0;
            posImage = random.nextInt(usedImages.size());
            while (j < 2) {
                posCeld = elements.get(random.nextInt(elements.size()));
                cards.get(posCeld).setImageResource(usedImages.get(posImage));
                elements.remove(Integer.valueOf(posCeld));
                j++;
            }
            usedImages.remove(posImage);
            cantImages--;
        }

        drawableLocation = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++) {
            drawableLocation.add(cards.get(i).getDrawable());
        }

        handler.postDelayed(new Runnable() {
            public void run() {
                for (int i = 0; i < cards.size(); i++) {
                    startChronometer();
                    cards.get(i).setImageResource(R.drawable.dona);
                    cards.get(i).setEnabled(true);
                }
            }
        }, 5000);   //5 seconds

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == buttonBack.getId())
            onBackPressed();
        else if (v.getId() == buttonSound.getId()) {
            amanager.setStreamMute(AudioManager.STREAM_MUSIC, !buttonSound.isChecked());
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
                    for (int i = 0; i < cards.size(); i++) {
                        if (cards.get(i).getVisibility() == View.VISIBLE)
                            cards.get(i).setEnabled(false);
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
                            for (int i = 0; i < cards.size(); i++) {
                                if (cards.get(i).getVisibility() == View.VISIBLE)
                                    cards.get(i).setEnabled(true);
                            }
                            firstCard = null;
                            secondCard = null;
                            firstImage = null;
                            secondImage = null;
                        }

                    }, 3000);   //5 seconds
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

        startActivity(i);
    }

    public void calculateFinalPoints(int seconds){
        finalPoints = ((hits * 100) / (errors + elapsed))*10;
    }

    public void repaintEasy(View v) {
        switch (v.getId()) {
            case R.id.card1:
                cards.get(0).setImageDrawable(drawableLocation.get(0));
                break;
            case R.id.card2:
                cards.get(1).setImageDrawable(drawableLocation.get(1));
                break;
            case R.id.card3:
                cards.get(2).setImageDrawable(drawableLocation.get(2));
                break;
            case R.id.card4:
                cards.get(3).setImageDrawable(drawableLocation.get(3));
                break;
            case R.id.card6:
                cards.get(4).setImageDrawable(drawableLocation.get(4));
                break;
            case R.id.card7:
                cards.get(5).setImageDrawable(drawableLocation.get(5));
                break;
            case R.id.card8:
                cards.get(6).setImageDrawable(drawableLocation.get(6));
                break;
            case R.id.card9:
                cards.get(7).setImageDrawable(drawableLocation.get(7));
                break;
            case R.id.card11:
                cards.get(8).setImageDrawable(drawableLocation.get(8));
                break;
            case R.id.card12:
                cards.get(9).setImageDrawable(drawableLocation.get(9));
                break;
            case R.id.card13:
                cards.get(10).setImageDrawable(drawableLocation.get(10));
                break;
            case R.id.card14:
                cards.get(11).setImageDrawable(drawableLocation.get(11));
                break;
        }
    }

    public void repaintNormal(View v) {
        switch (v.getId()) {
            case R.id.card1:
                cards.get(0).setImageDrawable(drawableLocation.get(0));
                break;
            case R.id.card2:
                cards.get(1).setImageDrawable(drawableLocation.get(1));
                break;
            case R.id.card3:
                cards.get(2).setImageDrawable(drawableLocation.get(2));
                break;
            case R.id.card4:
                cards.get(3).setImageDrawable(drawableLocation.get(3));
                break;
            case R.id.card6:
                cards.get(4).setImageDrawable(drawableLocation.get(4));
                break;
            case R.id.card7:
                cards.get(5).setImageDrawable(drawableLocation.get(5));
                break;
            case R.id.card8:
                cards.get(6).setImageDrawable(drawableLocation.get(6));
                break;
            case R.id.card9:
                cards.get(7).setImageDrawable(drawableLocation.get(7));
                break;
            case R.id.card11:
                cards.get(8).setImageDrawable(drawableLocation.get(8));
                break;
            case R.id.card12:
                cards.get(9).setImageDrawable(drawableLocation.get(9));
                break;
            case R.id.card13:
                cards.get(10).setImageDrawable(drawableLocation.get(10));
                break;
            case R.id.card14:
                cards.get(11).setImageDrawable(drawableLocation.get(11));
                break;
            case R.id.card16:
                cards.get(12).setImageDrawable(drawableLocation.get(12));
                break;
            case R.id.card17:
                cards.get(13).setImageDrawable(drawableLocation.get(13));
                break;
            case R.id.card18:
                cards.get(14).setImageDrawable(drawableLocation.get(14));
                break;
            case R.id.card19:
                cards.get(15).setImageDrawable(drawableLocation.get(15));
                break;
            case R.id.card21:
                cards.get(16).setImageDrawable(drawableLocation.get(16));
                break;
            case R.id.card22:
                cards.get(17).setImageDrawable(drawableLocation.get(17));
                break;
            case R.id.card23:
                cards.get(18).setImageDrawable(drawableLocation.get(18));
                break;
            case R.id.card24:
                cards.get(19).setImageDrawable(drawableLocation.get(19));
                break;
            case R.id.card26:
                cards.get(20).setImageDrawable(drawableLocation.get(20));
                break;
            case R.id.card27:
                cards.get(21).setImageDrawable(drawableLocation.get(21));
                break;
            case R.id.card28:
                cards.get(22).setImageDrawable(drawableLocation.get(22));
                break;
            case R.id.card29:
                cards.get(23).setImageDrawable(drawableLocation.get(23));
                break;
        }
    }

    public void repaintHard(View v) {
        switch (v.getId()) {
            case R.id.card1:
                cards.get(0).setImageDrawable(drawableLocation.get(0));
                break;
            case R.id.card2:
                cards.get(1).setImageDrawable(drawableLocation.get(1));
                break;
            case R.id.card3:
                cards.get(2).setImageDrawable(drawableLocation.get(2));
                break;
            case R.id.card4:
                cards.get(3).setImageDrawable(drawableLocation.get(3));
                break;
            case R.id.card5:
                cards.get(4).setImageDrawable(drawableLocation.get(4));
                break;
            case R.id.card6:
                cards.get(5).setImageDrawable(drawableLocation.get(5));
                break;
            case R.id.card7:
                cards.get(6).setImageDrawable(drawableLocation.get(6));
                break;
            case R.id.card8:
                cards.get(7).setImageDrawable(drawableLocation.get(7));
                break;
            case R.id.card9:
                cards.get(8).setImageDrawable(drawableLocation.get(8));
                break;
            case R.id.card10:
                cards.get(9).setImageDrawable(drawableLocation.get(9));
                break;
            case R.id.card11:
                cards.get(10).setImageDrawable(drawableLocation.get(10));
                break;
            case R.id.card12:
                cards.get(11).setImageDrawable(drawableLocation.get(11));
                break;
            case R.id.card13:
                cards.get(12).setImageDrawable(drawableLocation.get(12));
                break;
            case R.id.card14:
                cards.get(13).setImageDrawable(drawableLocation.get(13));
                break;
            case R.id.card15:
                cards.get(14).setImageDrawable(drawableLocation.get(14));
                break;
            case R.id.card16:
                cards.get(15).setImageDrawable(drawableLocation.get(15));
                break;
            case R.id.card17:
                cards.get(16).setImageDrawable(drawableLocation.get(16));
                break;
            case R.id.card18:
                cards.get(17).setImageDrawable(drawableLocation.get(17));
                break;
            case R.id.card19:
                cards.get(18).setImageDrawable(drawableLocation.get(18));
                break;
            case R.id.card20:
                cards.get(19).setImageDrawable(drawableLocation.get(19));
                break;
            case R.id.card21:
                cards.get(20).setImageDrawable(drawableLocation.get(20));
                break;
            case R.id.card22:
                cards.get(21).setImageDrawable(drawableLocation.get(21));
                break;
            case R.id.card23:
                cards.get(22).setImageDrawable(drawableLocation.get(22));
                break;
            case R.id.card24:
                cards.get(23).setImageDrawable(drawableLocation.get(23));
                break;
            case R.id.card25:
                cards.get(24).setImageDrawable(drawableLocation.get(24));
                break;
            case R.id.card26:
                cards.get(25).setImageDrawable(drawableLocation.get(25));
                break;
            case R.id.card27:
                cards.get(26).setImageDrawable(drawableLocation.get(26));
                break;
            case R.id.card28:
                cards.get(27).setImageDrawable(drawableLocation.get(27));
                break;
            case R.id.card29:
                cards.get(28).setImageDrawable(drawableLocation.get(28));
                break;
            case R.id.card30:
                cards.get(29).setImageDrawable(drawableLocation.get(29));
                break;
            case R.id.card31:
                cards.get(30).setImageDrawable(drawableLocation.get(30));
                break;
            case R.id.card32:
                cards.get(31).setImageDrawable(drawableLocation.get(31));
                break;
            case R.id.card33:
                cards.get(32).setImageDrawable(drawableLocation.get(32));
                break;
            case R.id.card34:
                cards.get(33).setImageDrawable(drawableLocation.get(33));
                break;
            case R.id.card35:
                cards.get(34).setImageDrawable(drawableLocation.get(34));
                break;
            case R.id.card36:
                cards.get(35).setImageDrawable(drawableLocation.get(35));
                break;
            case R.id.card37:
                cards.get(36).setImageDrawable(drawableLocation.get(36));
                break;
            case R.id.card38:
                cards.get(37).setImageDrawable(drawableLocation.get(37));
                break;
            case R.id.card39:
                cards.get(38).setImageDrawable(drawableLocation.get(38));
                break;
            case R.id.card40:
                cards.get(39).setImageDrawable(drawableLocation.get(39));
                break;
        }
    }
}