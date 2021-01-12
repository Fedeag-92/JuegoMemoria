package com.example.juegomemoria;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

public class Juego extends AppCompatActivity {
    int points, record, difficulty;
    String user;
    ImageView cards[];
    Random random = new Random();
    int pos;
    ArrayList<Integer> imagenes = new ArrayList<>();
    ArrayList<Integer> imagenesUsadas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        imagenes.add(R.drawable.card1);
        imagenes.add(R.drawable.card2);
        imagenes.add(R.drawable.card3);
        imagenes.add(R.drawable.card4);
        imagenes.add(R.drawable.card5);
        imagenes.add(R.drawable.card6);
        imagenes.add(R.drawable.card7);
        imagenes.add(R.drawable.card8);
        imagenes.add(R.drawable.card9);
        imagenes.add(R.drawable.card10);
        imagenes.add(R.drawable.card11);
        imagenes.add(R.drawable.card12);
        imagenes.add(R.drawable.card13);
        imagenes.add(R.drawable.card14);
        imagenes.add(R.drawable.card15);
        imagenes.add(R.drawable.card16);
        imagenes.add(R.drawable.card17);
        imagenes.add(R.drawable.card18);
        imagenes.add(R.drawable.card19);
        imagenes.add(R.drawable.card20);

        imagenesUsadas = (ArrayList<Integer>) imagenes.clone();

        points = 0;
        difficulty = getIntent().getIntExtra("choice", 0);
        int k;

        switch (difficulty) {
            case 1:
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
                playEasy();

                break;
            case 2:
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

    public void playEasy() {
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
    }
}