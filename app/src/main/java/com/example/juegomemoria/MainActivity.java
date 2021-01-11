package com.example.juegomemoria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText user, pass;
    Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}