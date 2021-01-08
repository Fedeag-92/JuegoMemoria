package com.example.juegomemoria;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registro extends AppCompatActivity {
    EditText edtNombreusuario, edtNombre, edtApellido, edtClave;
    Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        edtNombreusuario = (EditText)findViewById(R.id.nombre_usuario);
        edtNombre = (EditText)findViewById(R.id.nombre);
        edtApellido = (EditText)findViewById(R.id.apellido);
        edtClave = (EditText)findViewById(R.id.clave);

        final bdjuegomemoria bdjuegomemoria= new bdjuegomemoria(getApplicationContext());
        btnRegistrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                bdjuegomemoria.agregarUsuario(edtNombreusuario.getText().toString(),edtNombre.getText().toString(),edtApellido.getText().toString(),edtClave.getText().toString());
                Toast.makeText(getApplicationContext(),"USUARIO REGISTRADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            }
        });
    }
}