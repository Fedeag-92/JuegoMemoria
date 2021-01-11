package com.example.juegomemoria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.juegomemoria.utlidades.Utilidades;

public class Registro extends AppCompatActivity {

    private EditText txt_nombre, txt_apellido, txt_username, txt_password;
    private ConexionSQLiteHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txt_nombre = (EditText) findViewById(R.id.nombre);
        txt_apellido = (EditText) findViewById(R.id.apellido);
        txt_username = (EditText) findViewById(R.id.username);
        txt_password = (EditText) findViewById(R.id.password);
        db = new ConexionSQLiteHelper(this,"bd_juegomemoria",null,1);

    }

    public void clickRegistro(View view) {

        String nombre = txt_nombre.getText().toString();
        String apellido = txt_apellido.getText().toString();
        String username = txt_username.getText().toString();
        String password = txt_password.getText().toString();

        if (nombre.length() == 0 || apellido.length() == 0 || username.length() == 0 || password.length() == 0) {
            Toast.makeText(this, "Faltan completar campos", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Registro en proceso...", Toast.LENGTH_LONG).show();

            db.execSQL("INSERT INTO USUARIO VALUES('"+ Utilidades.USERNAME+"','"+Utilidades.NOMBRE+"','"+Utilidades.APELLIDO+"','"+Utilidades.PASSWORD+"')");
            db.close();
            Intent i = new Intent(this, Dificultad.class);
            startActivity(i);
        }
    }

    public void clickRegresar(View view) {
        onBackPressed();
    }
}