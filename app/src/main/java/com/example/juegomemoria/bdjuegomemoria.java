package com.example.juegomemoria;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class bdjuegomemoria extends SQLiteOpenHelper {

    private static final String NOMBRE_BD="juegomemoria.bd";
    private static final int VERSION_BD=1;
    private static final String TABLA_USUARIO="CREATE TABLE USUARIO(NOMBRE_USUARIO TEXT PRIMARY KEY, NOMBRE TEXT, APELLIDO TEXT, CLAVE TEXT)";

    public bdjuegomemoria(Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLA_USUARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IFEXISTS"+TABLA_USUARIO);
        sqLiteDatabase.execSQL(TABLA_USUARIO);
    }

    public void agregarUsuario(String username,String nombre, String apellido, String password){
        SQLiteDatabase bd=getWritableDatabase();

        if(bd!=null){
            bd.execSQL("INSERT INTO USUARIO VALUES('"+username+"','"+nombre+"','"+apellido+"','"+password+"')");
            bd.close();
        }
    }
}
