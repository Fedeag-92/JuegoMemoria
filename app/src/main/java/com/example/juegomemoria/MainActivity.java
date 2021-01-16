package com.example.juegomemoria;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    MediaPlayer mediaPlayer;
    EditText user, pass;
    Button btnLogin, btnRegister;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        mediaPlayer = new MediaPlayer();
        playMP();

        user = (EditText) findViewById(R.id.loginUserName);
        pass = (EditText) findViewById(R.id.loginPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegistrar);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

        ConexionSQLiteHelper dbHelper = new ConexionSQLiteHelper(this, "bd_juegomemoria", null, 1);
        db = dbHelper.getWritableDatabase();
    }

    @Override
    public void onClick(View v) {
        String username = user.getText().toString();
        String password = pass.getText().toString();
        switch (v.getId()) {
            case R.id.btnLogin:
                if (username.length() != 0 && password.length() != 0) {
                    if (verificarPassword(username, password)) {
                        Intent i = new Intent(MainActivity.this, Dificultad.class);
                        i.putExtra("user", this.user.getText().toString());
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Campos incompletos", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnRegistrar:
                user.getText().clear();
                pass.getText().clear();
                Intent i = new Intent(MainActivity.this, Registro.class);
                startActivity(i);
                break;
        }
    }

    public boolean verificarPassword(String username, String pass) {
        Cursor c = db.rawQuery("SELECT username FROM usuario WHERE username = '" + username + "' AND password = '" + pass + "'", null);
        return (c.moveToFirst());
    }

    public void playMP() {
        Thread playThread = new Thread() {
            public void run() {
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.music);
                mediaPlayer.start();
            }
        };
        playThread.start();
    }

    public void stopMP() {
        mediaPlayer.stop();
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

}