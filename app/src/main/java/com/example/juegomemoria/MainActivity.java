package com.example.juegomemoria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.database.sqlite.SQLiteDatabase;
import android.content.Intent;
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
    private ConexionSQLiteHelper dbHelper;
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
        //ConexionSQLiteHelper conexion=new ConexionSQLiteHelper(this,"bd_juegomemoria",null,1);
        //SQLiteDatabase db = conexion.getWritableDatabase();

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnLogin:
                if(user.getText().toString().length() != 0 && pass.getText().toString().length() != 0){
                    Intent i = new Intent(MainActivity.this, Dificultad.class);
                    i.putExtra("user",this.user.getText().toString());
                    startActivity(i);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Error: debe completar ambos campos", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnRegistrar:
                Intent i = new Intent(MainActivity.this, Registro.class);
                startActivity(i);
                break;
        }
    }

    public void playMP(){
        Thread playThread = new Thread(){
            public void run(){
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.music);
                mediaPlayer.start();
            }
        };
        playThread.start();
    }

    public void stopMP(){
        mediaPlayer.stop();
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}