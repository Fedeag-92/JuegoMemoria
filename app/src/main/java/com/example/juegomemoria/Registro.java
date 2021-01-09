package com.example.juegomemoria;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registro extends AppCompatActivity {
    EditText txt_username, txt_nombre, txt_apellido, txt_password;
    Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txt_username = (EditText)findViewById(R.id.nombre_usuario);
        txt_nombre = (EditText)findViewById(R.id.nombre);
        txt_apellido = (EditText)findViewById(R.id.apellido);
        txt_password = (EditText)findViewById(R.id.clave);

        final bdjuegomemoria bdjuegomemoria= new bdjuegomemoria(getApplicationContext());
        btnRegistrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                String nombre = txt_nombre.getText().toString();
                String apellido = txt_apellido.getText().toString();
                String username = txt_username.getText().toString();
                String password = txt_password.getText().toString();

                if (nombre.length() == 0 || apellido.length() == 0 || username.length() == 0 || password.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Faltan completar campos", Toast.LENGTH_LONG).show();
                } else {
                    bdjuegomemoria.agregarUsuario(username, nombre, apellido, password);
                    Toast.makeText(getApplicationContext(), "Registro en proceso...", Toast.LENGTH_LONG).show();
                    //Intent i = new Intent(this, Dificultad.class);
                    //startActivity(i);
                }
            }
            public void clickRegresar(View view) {
                onBackPressed();
            }
        });
    }
}