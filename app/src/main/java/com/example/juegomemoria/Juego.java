package com.example.juegomemoria;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class Juego extends AppCompatActivity {
    int points, record, difficulty;
    String user;
    ImageView cards[];
    Random random = new Random();
    int pos;
    ArrayList<Integer> imagenes = new ArrayList<>();
    ArrayList<Integer> imagenesUsadas = new ArrayList<>();
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        imagenes.addAll(Arrays.asList(R.drawable.card1, R.drawable.card2, R.drawable.card3, R.drawable.card4, R.drawable.card5, R.drawable.card6, R.drawable.card7, R.drawable.card8, R.drawable.card9, R.drawable.card10, R.drawable.card11, R.drawable.card12, R.drawable.card13, R.drawable.card14, R.drawable.card15, R.drawable.card16, R.drawable.card17, R.drawable.card18, R.drawable.card19, R.drawable.card20));

        imagenesUsadas = (ArrayList<Integer>) imagenes.clone();

        difficulty = getIntent().getIntExtra("choice", 0);
        int k;

        switch (difficulty) {
            case 1:
                points = 0;
                cards = new ImageView[12];
                k = 0;
                for (int j = 0; j < 14; j++) {
                    if (j != 4 && j != 9) {
                        String cardName = "card" + (j + 1);
                        int resIDcard = getResources().getIdentifier(cardName, "id", getPackageName());
                        cards[k] = ((ImageView) findViewById(resIDcard));
                        cards[k].setVisibility(View.VISIBLE);
                        k++;
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
                cards = new ImageView[24];
                k = 0;
                for (int j = 0; j < 29; j++) {
                    if (j != 4 && j != 9 && j != 14 && j != 19 && j != 24) {
                        String cardName = "card" + (j + 1);
                        int resIDcard = getResources().getIdentifier(cardName, "id", getPackageName());
                        cards[k] = ((ImageView) findViewById(resIDcard));
                        cards[k].setVisibility(View.VISIBLE);
                        k++;
                    }
                }

                for (int i = 0; i < 8; i++) {
                    imagenesUsadas.remove(random.nextInt(Integer.valueOf(imagenesUsadas.size())));
                }

                playNormal();
                break;
            case 3:
                points = 0;
                cards = new ImageView[40];
                for (int j = 0; j < 40; j++) {
                    String cardName = "card" + (j + 1);
                    int resIDcard = getResources().getIdentifier(cardName, "id", getPackageName());
                    cards[j] = ((ImageView) findViewById(resIDcard));
                    cards[j].setVisibility(View.VISIBLE);
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
            cards[pos].setImageResource(imagenesUsadas.get(imgRandom));
            elements.remove(Integer.valueOf(pos));
            pos = elements.get(random.nextInt(elements.size()));
            cards[pos].setImageResource(imagenesUsadas.get(imgRandom));
            elements.remove(Integer.valueOf(pos));
            imagenesUsadas.remove(imgRandom);
            j--;
        }

        handler.postDelayed(new Runnable() {
            public void run() {
                for (int i = 0; i < cards.length; i++) {
                    cards[i].setImageResource(R.drawable.dona);
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
            cards[pos].setImageResource(imagenesUsadas.get(imgRandom));
            elements.remove(Integer.valueOf(pos));
            pos = elements.get(random.nextInt(elements.size()));
            cards[pos].setImageResource(imagenesUsadas.get(imgRandom));
            elements.remove(Integer.valueOf(pos));
            imagenesUsadas.remove(imgRandom);
            j--;
        }

        handler.postDelayed(new Runnable() {
            public void run() {
                for (int i = 0; i < cards.length; i++) {
                    cards[i].setImageResource(R.drawable.dona);
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
            cards[pos].setImageResource(imagenesUsadas.get(imgRandom));
            elements.remove(Integer.valueOf(pos));
            pos = elements.get(random.nextInt(elements.size()));
            cards[pos].setImageResource(imagenesUsadas.get(imgRandom));
            elements.remove(Integer.valueOf(pos));
            imagenesUsadas.remove(imgRandom);
            j--;
        }

        handler.postDelayed(new Runnable() {
            public void run() {
                for (int i = 0; i < cards.length; i++) {
                    cards[i].setImageResource(R.drawable.dona);
                }
            }
        }, 5000);   //5 seconds
    }
}