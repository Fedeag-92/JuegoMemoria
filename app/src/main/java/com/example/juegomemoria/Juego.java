package com.example.juegomemoria;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class Juego extends AppCompatActivity implements View.OnClickListener {
    int points, record, difficulty, aciertos, errores, erroresMax, turns;
    boolean gameOver = false;
    String user;
    ArrayList<ImageView> cards;
    Random random = new Random();
    int pos;
    ArrayList<Drawable> cardsSinOcultar;
    ArrayList<Integer> imagenes = new ArrayList<>();
    ArrayList<Integer> imagenesUsadas = new ArrayList<>();
    Handler handler = new Handler();
    private static Object lock = new Object();
    private ImageView firstCard;
    private ImageView secondCard;
    private Bitmap firstImage;
    private Bitmap secondImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        this.aciertos = 0;

        imagenes.addAll(Arrays.asList(R.drawable.card1, R.drawable.card2, R.drawable.card3, R.drawable.card4, R.drawable.card5, R.drawable.card6, R.drawable.card7, R.drawable.card8, R.drawable.card9, R.drawable.card10, R.drawable.card11, R.drawable.card12, R.drawable.card13, R.drawable.card14, R.drawable.card15, R.drawable.card16, R.drawable.card17, R.drawable.card18, R.drawable.card19, R.drawable.card20));

        imagenesUsadas = new ArrayList(imagenes);

        difficulty = getIntent().getIntExtra("choice", 0);
        int k;

        switch (difficulty) {
            case 1:
                turns = 6;
                erroresMax = Integer.MAX_VALUE;
                points = 0;
                ImageView card;
                cards = new ArrayList<ImageView>(12);
                for (int j = 0; j < 14; j++) {
                    if (j != 4 && j != 9) {
                        String cardName = "card" + (j + 1);
                        int resIDcard = getResources().getIdentifier(cardName, "id", getPackageName());
                        card = ((ImageView) findViewById(resIDcard));
                        card.setOnClickListener(this);
                        card.setVisibility(View.VISIBLE);
                        card.setEnabled(false);
                        cards.add(card);
                    }
                }

                for (int i = 0; i < 14; i++) {
                    imagenesUsadas.remove(random.nextInt(Integer.valueOf(imagenesUsadas.size())));
                }
                try {
                    playEasy();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                break;
            case 2:
                points = 0;
                cards = new ArrayList<ImageView>(24);
                for (int j = 0; j < 29; j++) {
                    if (j != 4 && j != 9 && j != 14 && j != 19 && j != 24) {
                        String cardName = "card" + (j + 1);
                        int resIDcard = getResources().getIdentifier(cardName, "id", getPackageName());
                        card = ((ImageView) findViewById(resIDcard));
                        card.setOnClickListener(this);
                        card.setVisibility(View.VISIBLE);
                        card.setEnabled(false);
                        cards.add(card);
                    }
                }

                for (int i = 0; i < 8; i++) {
                    imagenesUsadas.remove(random.nextInt(Integer.valueOf(imagenesUsadas.size())));
                }

                playNormal();
                break;
            case 3:
                points = 0;
                cards = new ArrayList<ImageView>(40);
                for (int j = 0; j < 40; j++) {
                    String cardName = "card" + (j + 1);
                    int resIDcard = getResources().getIdentifier(cardName, "id", getPackageName());
                    card = ((ImageView) findViewById(resIDcard));
                    card.setOnClickListener(this);
                    card.setVisibility(View.VISIBLE);
                    card.setEnabled(false);
                    cards.add(card);
                }

                playHard();
                break;
        }
    }

    public void playEasy() throws InterruptedException {
        int j = 6;
        int imgRandom;

        ArrayList<Integer> elements = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            elements.add(i);
        }

        while (j > 0) {
            pos = elements.get(random.nextInt(elements.size()));
            imgRandom = random.nextInt(imagenesUsadas.size());
            cards.get(pos).setImageResource(imagenesUsadas.get(imgRandom));
            elements.remove(Integer.valueOf(pos));
            pos = elements.get(random.nextInt(elements.size()));
            cards.get(pos).setImageResource(imagenesUsadas.get(imgRandom));
            elements.remove(Integer.valueOf(pos));
            imagenesUsadas.remove(imgRandom);
            j--;
        }

        cardsSinOcultar = new ArrayList();
        cardsSinOcultar.addAll(Arrays.asList(cards.get(0).getDrawable(),
                cards.get(1).getDrawable(),
                cards.get(2).getDrawable(),
                cards.get(3).getDrawable(),
                cards.get(4).getDrawable(),
                cards.get(5).getDrawable(),
                cards.get(6).getDrawable(),
                cards.get(7).getDrawable(),
                cards.get(8).getDrawable(),
                cards.get(9).getDrawable(),
                cards.get(10).getDrawable(),
                cards.get(11).getDrawable()));

        handler.postDelayed(new Runnable() {
            public void run() {
                for (int i = 0; i < cards.size(); i++) {
                    cards.get(i).setImageResource(R.drawable.dona);
                    cards.get(i).setEnabled(true);
                }
            }
        }, 5000);   //5 seconds


    }


    public void playNormal() {
        int j = 12;
        int imgRandom;

        ArrayList<Integer> elements = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            elements.add(i);
        }

        while (j > 0) {
            pos = elements.get(random.nextInt(elements.size()));
            imgRandom = random.nextInt(imagenesUsadas.size());
            cards.get(pos).setImageResource(imagenesUsadas.get(imgRandom));
            elements.remove(Integer.valueOf(pos));
            pos = elements.get(random.nextInt(elements.size()));
            cards.get(pos).setImageResource(imagenesUsadas.get(imgRandom));
            elements.remove(Integer.valueOf(pos));
            imagenesUsadas.remove(imgRandom);
            j--;
        }

        handler.postDelayed(new Runnable() {
            public void run() {
                for (int i = 0; i < cards.size(); i++) {
                    cards.get(i).setImageResource(R.drawable.dona);
                }
            }
        }, 5000);   //5 seconds
    }

    public void playHard() {
        int j = 20;
        int imgRandom;

        ArrayList<Integer> elements = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            elements.add(i);
        }

        while (j > 0) {
            pos = elements.get(random.nextInt(elements.size()));
            imgRandom = random.nextInt(imagenesUsadas.size());
            cards.get(pos).setImageResource(imagenesUsadas.get(imgRandom));
            elements.remove(Integer.valueOf(pos));
            pos = elements.get(random.nextInt(elements.size()));
            cards.get(pos).setImageResource(imagenesUsadas.get(imgRandom));
            elements.remove(Integer.valueOf(pos));
            imagenesUsadas.remove(imgRandom);
            j--;
        }

        handler.postDelayed(new Runnable() {
            public void run() {
                for (int i = 0; i < cards.size(); i++) {
                    cards.get(i).setImageResource(R.drawable.dona);
                }
            }
        }, 5000);   //5 seconds
    }


    @Override
    public void onClick(View view) {
        if (!gameOver) {
            switch (view.getId()) {
                case R.id.card1:
                    cards.get(0).setImageDrawable(cardsSinOcultar.get(0));
                    break;
                case R.id.card2:
                    cards.get(1).setImageDrawable(cardsSinOcultar.get(1));
                    break;
                case R.id.card3:
                    cards.get(2).setImageDrawable(cardsSinOcultar.get(2));
                    break;
                case R.id.card4:
                    cards.get(3).setImageDrawable(cardsSinOcultar.get(3));
                    break;
                case R.id.card6:
                    cards.get(4).setImageDrawable(cardsSinOcultar.get(4));
                    break;
                case R.id.card7:
                    cards.get(5).setImageDrawable(cardsSinOcultar.get(5));
                    break;
                case R.id.card8:
                    cards.get(6).setImageDrawable(cardsSinOcultar.get(6));
                    break;
                case R.id.card9:
                    cards.get(7).setImageDrawable(cardsSinOcultar.get(7));
                    break;
                case R.id.card11:
                    cards.get(8).setImageDrawable(cardsSinOcultar.get(8));
                    break;
                case R.id.card12:
                    cards.get(9).setImageDrawable(cardsSinOcultar.get(9));
                    break;
                case R.id.card13:
                    cards.get(10).setImageDrawable(cardsSinOcultar.get(10));
                    break;
                case R.id.card14:
                    cards.get(11).setImageDrawable(cardsSinOcultar.get(11));
                    break;
            }
            if (firstCard == null) {
                firstCard = (ImageView) findViewById(view.getId());
                firstImage = ((BitmapDrawable) (firstCard).getDrawable()).getBitmap();
                firstCard.setEnabled(false);
            } else {
                secondCard = (ImageView) findViewById(view.getId());
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
                            aciertos++;
                            Toast.makeText(getApplicationContext(), "Acierto! Total: " + aciertos, Toast.LENGTH_LONG).show();
                            firstCard.setVisibility(View.INVISIBLE);
                            secondCard.setVisibility(View.INVISIBLE);
                        } else {
                            errores++;
                            Toast.makeText(getApplicationContext(), "Error! Total: " + errores, Toast.LENGTH_LONG).show();
                            if(difficulty == 2 || difficulty == 3)
                                Toast.makeText(getApplicationContext(), erroresMax + " errores mas y pierdes", Toast.LENGTH_LONG).show();
                            firstCard.setImageResource(R.drawable.dona);
                            secondCard.setImageResource(R.drawable.dona);
                        }
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
        }
        else
            Toast.makeText(getApplicationContext(), "Juego terminado, empiece uno nuevo yendo hacia atras", Toast.LENGTH_LONG).show();

        if (!gameOver && errores >= erroresMax) {
            gameOver = true;
            Toast.makeText(getApplicationContext(), "Juego terminado, te has equivocado" + erroresMax + " veces", Toast.LENGTH_LONG).show();
        }
        if(!gameOver && turns == 0){
            gameOver = true;
            Toast.makeText(getApplicationContext(), "Juego terminado, has acertado todas las fichas!" + erroresMax + " veces", Toast.LENGTH_LONG).show();
        }
        if(gameOver){
            points = aciertos / errores;
            Toast.makeText(getApplicationContext(), "Puntaje final: " + points, Toast.LENGTH_LONG).show();
            if(points > record){
                record = points;
                Toast.makeText(getApplicationContext(), "Nuevo record!", Toast.LENGTH_LONG).show();
            }
        }

    }

}