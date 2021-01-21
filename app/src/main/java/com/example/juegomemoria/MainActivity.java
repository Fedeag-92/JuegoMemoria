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
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
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
    GifImageView loading;
    Button btnLogin, btnRegister;
    private ConexionSQLiteHelper dbHelper;
    private SQLiteDatabase db;
    TextInputLayout box_user, box_pass;
    boolean isRegistering = false;
    final Handler handler = new Handler();
    public static MediaPlayer mediaPlayer;
    ImageView btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnExit = (ImageView) findViewById(R.id.btnExit);
        btnExit.setOnClickListener(this);

        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.song);
        mediaPlayer.setVolume(0.07f, 0.07f);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        loading = (GifImageView) findViewById(R.id.imgLoading);
        imgMain = (ImageView) findViewById(R.id.imgMain);
        imgIntro = (ImageView) findViewById(R.id.imgIntro);
        box_user = (TextInputLayout) findViewById(R.id.box_username);
        box_pass = (TextInputLayout) findViewById(R.id.box_password);

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

        tittle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/simpson.ttf"));

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

        handler.postDelayed(new Runnable() {
            public void run() {
                imgIntro.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                imgMain.setVisibility(View.VISIBLE);
                tittle.setVisibility(View.VISIBLE);
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
        String username = user.getText().toString();
        String password = pass.getText().toString();

        this.conectarBD();

        switch (v.getId()) {
            case R.id.btnLogin:
                if (username.length() != 0 && password.length() != 0) {
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) loading.getLayoutParams();
                    layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.UNSET;
                    layoutParams.topToTop = R.id.box_username;
                    layoutParams.topMargin = 0;
                    loading.setLayoutParams(layoutParams);
                    loading.setVisibility(View.VISIBLE);

                    long random = (long) (Math.random() * 2000 + 1000);
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            if (verificarPassword(username, password)) {
                                loading.setVisibility(View.INVISIBLE);
                                Intent i = new Intent(MainActivity.this, Dificultad.class);
                                i.putExtra("user", user.getText().toString());
                                startActivity(i);
                            } else {
                                Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show();
                                loading.setVisibility(View.INVISIBLE);
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

}