package com.example.juegomemoria;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class Ranking extends AppCompatActivity {

    ArrayList<Usuario> listaUsuarios = new ArrayList<>();
    TableLayout tlTabla;

    TableRow fila;

    TextView tvUsuario, tvTelefono, tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tlTabla = findViewById(R.id.tlTabla);

        listaUsuarios.add(new Usuario("Sergio", "asdas", "sergio", "12345"));
        listaUsuarios.add(new Usuario("Laura", "gvfdsgfd", "laura", "12345"));
        listaUsuarios.add(new Usuario("Ana", "ghfh", "ana", "12345"));
        listaUsuarios.add(new Usuario("Juan", "hfghfg", "juan", "12345"));

        TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams layoutUsuario = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams layoutTelefono = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams layoutEmail = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

        // TABLA
        for(int i = -1 ; i < listaUsuarios.size() ; i++) {
            fila = new TableRow(this);
            fila.setLayoutParams(layoutFila);

            if(i == -1) {
                tvUsuario = new TextView(this);
                tvUsuario.setText("USUARIO");
                tvUsuario.setGravity(Gravity.CENTER);
                tvUsuario.setBackgroundColor(Color.BLACK);
                tvUsuario.setTextColor(Color.WHITE);
                tvUsuario.setPadding(10, 10, 10, 10);
                tvUsuario.setLayoutParams(layoutUsuario);
                fila.addView(tvUsuario);

                tvTelefono = new TextView(this);
                tvTelefono.setText("TELEFONO");
                tvTelefono.setGravity(Gravity.CENTER);
                tvTelefono.setBackgroundColor(Color.BLACK);
                tvTelefono.setTextColor(Color.WHITE);
                tvTelefono.setPadding(10, 10, 10, 10);
                tvTelefono.setLayoutParams(layoutUsuario);
                fila.addView(tvTelefono);

                tvEmail = new TextView(this);
                tvEmail.setText("EMAIL");
                tvEmail.setGravity(Gravity.CENTER);
                tvEmail.setBackgroundColor(Color.BLACK);
                tvEmail.setTextColor(Color.WHITE);
                tvEmail.setPadding(10, 10, 10, 10);
                tvEmail.setLayoutParams(layoutUsuario);
                fila.addView(tvEmail);

                tlTabla.addView(fila);
            } else {
                tvUsuario = new TextView(this);
                tvUsuario.setText(listaUsuarios.get(i).getUsername());
                tvUsuario.setPadding(10, 10, 10, 10);
                tvUsuario.setLayoutParams(layoutUsuario);
                fila.addView(tvUsuario);

                tvTelefono = new TextView(this);
                tvTelefono.setGravity(Gravity.CENTER);
                tvTelefono.setText(listaUsuarios.get(i).getApellido());
                tvTelefono.setPadding(10, 10, 10, 10);
                tvTelefono.setLayoutParams(layoutTelefono);
                fila.addView(tvTelefono);

                tvEmail = new TextView(this);
                tvEmail.setText(listaUsuarios.get(i).getNombre());
                tvEmail.setPadding(10, 10, 10, 10);
                tvEmail.setLayoutParams(layoutEmail);
                fila.addView(tvEmail);

                tlTabla.addView(fila);
            }
        }
    }
}