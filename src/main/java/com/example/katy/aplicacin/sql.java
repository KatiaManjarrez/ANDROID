package com.example.katy.aplicacin;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class sql extends SQLiteOpenHelper {

    private static final String DATABASE = "CONTACTOS";
    private static final int VERSION=1;

    private final String tprod="CREATE TABLE PERSONAS( ID_PERSONA INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE TEXT NOT NULL, " +
            "ORGANIZACION TEXT NOT NULL, CUMPLEANIOS TEXT NOT NULL, SEXO TEXT NOT NULL, TELEFONO INTEGER NOT NULL, FOTO TEXT NOT NULL)";

    private final String tlista="CREATE TABLE PRODUCTOS( ID_PRODUCTO INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE_PRODUCTO TEXT NOT NULL,CANTIDAD INTEGER NOT NULL)";

    private final String talarm="CREATE TABLE ALARMA( idal INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE_PRODUCTO TEXT NOT NULL,FECHA DATE NOT NULL,HORA TIME NOT NULL)";



    public sql(Context context){
        super(context, DATABASE, null,VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tprod);
        db.execSQL(tlista);
        db.execSQL(talarm);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion>oldVersion){
            db.execSQL("DROP TABLE IF EXISTS PERSONAS");
            db.execSQL(tprod);
            db.execSQL(tlista);
            db.execSQL(talarm);
        }
    }
}
