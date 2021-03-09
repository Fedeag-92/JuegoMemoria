package com.example.juegomemoria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import pl.droidsonroids.gif.GifImageView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputEditText user, pass;
    private TextView title;
    private ImageView imgMain, imgIntro,btnExit;
    private GifImageView loading;
    private Button btnLogin, btnRegister;
    private ConexionSQLiteHelper dbHelper;
    private SQLiteDatabase db;
    private TextInputLayout box_user, box_pass;
    private final Handler handler = new Handler();
    public static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnExit = (ImageView) findViewById(R.id.btnExit);

        //Iniciar musica
        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.song);
        mediaPlayer.setVolume(0.07f, 0.07f);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        //Vinculacion de variables con objetos graficos
        loading = (GifImageView) findViewById(R.id.imgLoading);
        imgMain = (ImageView) findViewById(R.id.imgMain);
        imgIntro = (ImageView) findViewById(R.id.imgIntro);
        box_user = (TextInputLayout) findViewById(R.id.box_username);
        box_pass = (TextInputLayout) findViewById(R.id.box_password);
        title = (TextView) findViewById(R.id.titleMain);
        user = (TextInputEditText) findViewById(R.id.userNameMain);
        pass = (TextInputEditText) findViewById(R.id.passwordMain);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegisterM);

        //Animaciones de inicio de juego y carga.
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

        title.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/simpson.ttf"));

        btnExit.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

        //Handler ejecuta el bloque de codigo despues de un determinado tiempo.
        handler.postDelayed(new Runnable() {
            public void run() {
                imgIntro.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                imgMain.setVisibility(View.VISIBLE);
                title.setVisibility(View.VISIBLE);
                box_pass.setVisibility(View.VISIBLE);
                box_user.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.VISIBLE);
                btnRegister.setVisibility(View.VISIBLE);
                btnExit.setVisibility(View.VISIBLE);
            }
        }, 4250);   //5 seconds

    }

    @Override
    public void onClick(View v) {
        this.conectarBD();

        switch (v.getId()) {
            case R.id.btnLogin:
                String username = user.getText().toString();
                String password = pass.getText().toString();
                if (username.length() != 0 && password.length() != 0) {

                    this.modificarPosLoading();
                    long random = (long) (Math.random() * 2000 + 1000);
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            if (verificarPassword(username, password)) {
                                Intent i = new Intent(MainActivity.this, Dificultad.class);
                                i.putExtra("user", user.getText().toString());
                                startActivity(i);
                            } else {
                                Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show();
                            }
                            loading.setVisibility(View.INVISIBLE);
                        }
                    }, random);
                } else {
                    Toast.makeText(getApplicationContext(), "Campos incompletos", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnRegisterM:
                Intent i = new Intent(MainActivity.this, Registro.class);
                startActivity(i);
                break;

            case R.id.btnExit:
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("¿Deseas salir de la aplicación?");
                builder.setCancelable(true);

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishAffinity();
                        System.exit(0);
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
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

    public void conectarBD() {
        dbHelper = new ConexionSQLiteHelper(this, "bd_juegomemoria", null, 1);
        db = dbHelper.getWritableDatabase();
    }

    public void onResume() {
        super.onResume();
        user.setText("");
        pass.setText("");
    }

    public void modificarPosLoading(){
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) loading.getLayoutParams();
        layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.UNSET;
        layoutParams.topToTop = R.id.titleMain;
        layoutParams.topMargin = 0;
        loading.setLayoutParams(layoutParams);
        loading.setVisibility(View.VISIBLE);
    }

}