package com.example.juegomemoria;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.juegomemoria.utlidades.Utilidades;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    public ConexionSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilidades.TABLA_USUARIO);
        db.execSQL(Utilidades.TABLA_RANKING);
        //db.execSQL(Utilidades.TABLA_IMAGEN);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS USUARIO");
        db.execSQL("DROP TABLE IF EXISTS RANKING");
        onCreate(db);
    }

    public void insertar(String s, SQLiteDatabase db) {
        db.execSQL(s);
    }
}
