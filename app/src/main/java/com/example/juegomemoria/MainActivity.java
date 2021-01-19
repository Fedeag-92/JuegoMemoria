package com.example.juegomemoria;

import androidx.appcompat.app.AppCompatActivity;
import pl.droidsonroids.gif.GifImageView;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputEditText user, pass;
    TextView tittle;
    ImageView imgMain, imgIntro;
    GifImageView loading, verif;
    Button btnLogin, btnRegister;
    private ConexionSQLiteHelper dbHelper;
    private SQLiteDatabase db;
    TextInputLayout box_user, box_pass;
    boolean isRegistering = false;
    final Handler handler = new Handler();
    public static MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.song);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        loading = (GifImageView) findViewById(R.id.imgLoading);
        verif = (GifImageView) findViewById(R.id.logVerif);
        imgMain = (ImageView) findViewById(R.id.imgMain);
        imgIntro = (ImageView) findViewById(R.id.imgIntro);
        box_user = (TextInputLayout)findViewById(R.id.box_username);
        box_pass = (TextInputLayout)findViewById(R.id.box_password);


        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(1000);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setStartOffset(3250);
        fadeOut.setDuration(1000);

        AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(fadeIn);
        animation.addAnimation(fadeOut);
        loading.setAnimation(animation);
        imgIntro.setAnimation(animation);

        tittle = (TextView) findViewById(R.id.tittleMain);
        user = (TextInputEditText) findViewById(R.id.userNameMain);
        pass = (TextInputEditText) findViewById(R.id.passwordMain);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegisterM);

        tittle.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/simpson.ttf"));
        tittle.setTextSize(65);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);



        handler.postDelayed(new Runnable() {
            public void run() {
                imgIntro.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                imgMain.setVisibility(View.VISIBLE);
                tittle.setVisibility(View.VISIBLE);
                user.setVisibility(View.VISIBLE);
                pass.setVisibility(View.VISIBLE);
                box_pass.setVisibility(View.VISIBLE);
                box_user.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.VISIBLE);
                btnRegister.setVisibility(View.VISIBLE);
            }
        }, 4250);   //5 seconds

    }

    @Override
    public void onClick(View v) {
        String username = user.getText().toString();
        String password = pass.getText().toString();

        this.conectarBD();

        switch (v.getId()) {
            case R.id.btnLogin:
                if (username.length() != 0 && password.length() != 0) {
                    verif.setVisibility(View.VISIBLE);
                    long random = (long) (Math.random() * 3000 + 2000);
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            if (verificarPassword(username, password)) {
                                verif.setVisibility(View.INVISIBLE);
                                Intent i = new Intent(MainActivity.this, Dificultad.class);
                                i.putExtra("user", user.getText().toString());
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show();
                                verif.setVisibility(View.INVISIBLE);
                            }
                        }
                    }, random);
                } else {
                    Toast.makeText(getApplicationContext(), "Campos incompletos", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnRegisterM:
                user.setText("");
                pass.setText("");
                isRegistering = true;
                Intent i = new Intent(MainActivity.this, Registro.class);
                startActivity(i);
                break;
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); //Este metodo quita el teclado al hacer click en ingresar
        imm.hideSoftInputFromWindow(btnLogin.getWindowToken(), 0);
    }

    public boolean verificarPassword(String username, String pass) {
        Cursor c = db.rawQuery("SELECT username FROM usuario WHERE username = '" + username + "' AND password = '" + pass + "'", null);
        return (c.moveToFirst());
    }

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void conectarBD(){
        dbHelper = new ConexionSQLiteHelper(this, "bd_juegomemoria", null, 1);
        db = dbHelper.getWritableDatabase();
    }

    public void onResume() {
        super.onResume();
        user.setText("");
        pass.setText("");
    }

}