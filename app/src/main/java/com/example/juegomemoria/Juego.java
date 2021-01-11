package com.example.juegomemoria;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

public class Juego extends AppCompatActivity {
    int points, record, difficulty;
    String user;
    ImageView cards [];
    Random random = new Random();
    int pos;
    ArrayList<Integer> imagenesID = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        imagenesID.add(R.drawable.card1);
        imagenesID.add(R.drawable.card2);
        imagenesID.add(R.drawable.card3);
        imagenesID.add(R.drawable.card4);
        imagenesID.add(R.drawable.card5);
        imagenesID.add(R.drawable.card6);

        points = 0;
        difficulty = getIntent().getIntExtra("choice",0);


        switch (difficulty){
            case 1:
                cards = new ImageView[12];
                cards[0] = (ImageView) findViewById(R.id.card1);
                cards[1] = (ImageView) findViewById(R.id.card2);
                cards[2] = (ImageView) findViewById(R.id.card3);
                cards[3] = (ImageView) findViewById(R.id.card4);
                cards[4] = (ImageView) findViewById(R.id.card6);
                cards[5] = (ImageView) findViewById(R.id.card7);
                cards[6] = (ImageView) findViewById(R.id.card8);
                cards[7] = (ImageView) findViewById(R.id.card9);
                cards[8] = (ImageView) findViewById(R.id.card11);
                cards[9] = (ImageView) findViewById(R.id.card12);
                cards[10] = (ImageView) findViewById(R.id.card13);
                cards[11] = (ImageView) findViewById(R.id.card14);
                playEasy();

            break;
            case 2: playNormal(); break;
            case 3: playHard(); break;
        }


    }

    public void playEasy(){
        for (int i = 0; i < cards.length; i++) {
            cards[i].setVisibility(View.VISIBLE);
        }
        int j = 6;
        int imgRandom;
        ArrayList<Integer> elements = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            elements.add(i);
        }

        while(j > 0){
            pos = elements.get(random.nextInt(elements.size()));
            imgRandom = random.nextInt(imagenesID.size());
            cards[pos].setImageResource(imagenesID.get(imgRandom));
            elements.remove(Integer.valueOf(pos));
            pos = elements.get(random.nextInt(elements.size()));
            cards[pos].setImageResource(imagenesID.get(imgRandom));
            elements.remove(Integer.valueOf(pos));
            imagenesID.remove(imgRandom);
            j--;
        }
    }

    public void playNormal(){
        findViewById(R.id.card1).setVisibility(View.VISIBLE);
        findViewById(R.id.card2).setVisibility(View.VISIBLE);
        findViewById(R.id.card3).setVisibility(View.VISIBLE);
        findViewById(R.id.card4).setVisibility(View.VISIBLE);
        findViewById(R.id.card6).setVisibility(View.VISIBLE);
        findViewById(R.id.card7).setVisibility(View.VISIBLE);
        findViewById(R.id.card8).setVisibility(View.VISIBLE);
        findViewById(R.id.card9).setVisibility(View.VISIBLE);
        findViewById(R.id.card11).setVisibility(View.VISIBLE);
        findViewById(R.id.card12).setVisibility(View.VISIBLE);
        findViewById(R.id.card13).setVisibility(View.VISIBLE);
        findViewById(R.id.card14).setVisibility(View.VISIBLE);
        findViewById(R.id.card16).setVisibility(View.VISIBLE);
        findViewById(R.id.card17).setVisibility(View.VISIBLE);
        findViewById(R.id.card18).setVisibility(View.VISIBLE);
        findViewById(R.id.card19).setVisibility(View.VISIBLE);
        findViewById(R.id.card21).setVisibility(View.VISIBLE);
        findViewById(R.id.card22).setVisibility(View.VISIBLE);
        findViewById(R.id.card23).setVisibility(View.VISIBLE);
        findViewById(R.id.card24).setVisibility(View.VISIBLE);
        findViewById(R.id.card26).setVisibility(View.VISIBLE);
        findViewById(R.id.card27).setVisibility(View.VISIBLE);
        findViewById(R.id.card28).setVisibility(View.VISIBLE);
        findViewById(R.id.card29).setVisibility(View.VISIBLE);
    }

    public void playHard(){
        findViewById(R.id.card1).setVisibility(View.VISIBLE);
        findViewById(R.id.card2).setVisibility(View.VISIBLE);
        findViewById(R.id.card3).setVisibility(View.VISIBLE);
        findViewById(R.id.card4).setVisibility(View.VISIBLE);
        findViewById(R.id.card5).setVisibility(View.VISIBLE);
        findViewById(R.id.card6).setVisibility(View.VISIBLE);
        findViewById(R.id.card7).setVisibility(View.VISIBLE);
        findViewById(R.id.card8).setVisibility(View.VISIBLE);
        findViewById(R.id.card9).setVisibility(View.VISIBLE);
        findViewById(R.id.card10).setVisibility(View.VISIBLE);
        findViewById(R.id.card11).setVisibility(View.VISIBLE);
        findViewById(R.id.card12).setVisibility(View.VISIBLE);
        findViewById(R.id.card13).setVisibility(View.VISIBLE);
        findViewById(R.id.card14).setVisibility(View.VISIBLE);
        findViewById(R.id.card15).setVisibility(View.VISIBLE);
        findViewById(R.id.card16).setVisibility(View.VISIBLE);
        findViewById(R.id.card17).setVisibility(View.VISIBLE);
        findViewById(R.id.card18).setVisibility(View.VISIBLE);
        findViewById(R.id.card19).setVisibility(View.VISIBLE);
        findViewById(R.id.card20).setVisibility(View.VISIBLE);
        findViewById(R.id.card21).setVisibility(View.VISIBLE);
        findViewById(R.id.card22).setVisibility(View.VISIBLE);
        findViewById(R.id.card23).setVisibility(View.VISIBLE);
        findViewById(R.id.card24).setVisibility(View.VISIBLE);
        findViewById(R.id.card25).setVisibility(View.VISIBLE);
        findViewById(R.id.card26).setVisibility(View.VISIBLE);
        findViewById(R.id.card27).setVisibility(View.VISIBLE);
        findViewById(R.id.card28).setVisibility(View.VISIBLE);
        findViewById(R.id.card29).setVisibility(View.VISIBLE);
        findViewById(R.id.card30).setVisibility(View.VISIBLE);
        findViewById(R.id.card31).setVisibility(View.VISIBLE);
        findViewById(R.id.card32).setVisibility(View.VISIBLE);
        findViewById(R.id.card33).setVisibility(View.VISIBLE);
        findViewById(R.id.card34).setVisibility(View.VISIBLE);
        findViewById(R.id.card35).setVisibility(View.VISIBLE);
        findViewById(R.id.card36).setVisibility(View.VISIBLE);
        findViewById(R.id.card37).setVisibility(View.VISIBLE);
        findViewById(R.id.card38).setVisibility(View.VISIBLE);
        findViewById(R.id.card39).setVisibility(View.VISIBLE);
        findViewById(R.id.card40).setVisibility(View.VISIBLE);
    }
}