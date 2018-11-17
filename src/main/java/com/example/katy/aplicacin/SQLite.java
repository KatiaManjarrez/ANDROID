package com.example.katy.aplicacin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.VisibleForTesting;
import android.text.Editable;
import android.util.Log;
import android.view.textclassifier.TextClassificationSessionId;

import java.util.ArrayList;

public class SQLite{

    private sql sql;
    private SQLiteDatabase db;

    public SQLite(Context context){
        sql = new sql (context);
    }

    public void  abrir(){
        Log.i("SQLite", "Se abre conexion a la base de datos "+sql.getDatabaseName());
        db=sql.getWritableDatabase();
    }

    public void  cerrar(){
        Log.i("SQLite", "Se cierra conexion a la base de datos "+sql.getDatabaseName());
        sql.close();
    }

    public boolean addRegistro(String nom, String org, String cumple, String sexo,
                               int tel, String foto){

        ContentValues cv = new ContentValues();

        cv.put("NOMBRE", nom);
        cv.put("ORGANIZACION", org);
        cv.put("CUMPLEANIOS", cumple);
        cv.put("SEXO",sexo);
        cv.put("TELEFONO", tel);
        cv.put("FOTO", foto);

        return (db.insert("PERSONAS", null,cv) != -1)?true:false;
    }

    public boolean addRegistroProducto( String nom,int cantid){

        ContentValues cv = new ContentValues();


        cv.put("NOMBRE_PRODUCTO", nom);
        cv.put("CANTIDAD", cantid);


        return (db.insert("PRODUCTOS", null,cv) != -1)?true:false;
    }

    public boolean addRegistroAlarma(String nombrep,String fecha , String hora){

        ContentValues cv = new ContentValues();
        cv.put("NOMBRE_PRODUCTO",nombrep);
        cv.put("FECHA", fecha);
        cv.put("HORA", hora);


        return (db.insert("ALARMA", null,cv) != -1)?true:false;
    }

    public Cursor getRegistros(){
        return db.rawQuery("SELECT * FROM PERSONAS", null);
    }

    public Cursor getRegistrosP(){
        return db.rawQuery("SELECT * FROM PRODUCTOS", null);
    }

    public Cursor getRegistrosA(){
        return db.rawQuery("SELECT * FROM ALARMA", null);
    }


    public ArrayList<String> getPER(Cursor cur){
        ArrayList<String> listData = new ArrayList<String>();
        String item="";

        if(cur.moveToFirst()){
            do{
                item="ID del producto: ["+cur.getInt(0)+"]\r\n"
                        +"Nombre del producto: "+cur.getString(1)+"\r\n"
                        +"Ubicación del producto : "+cur.getString(2)+"\r\n"
                        +"Fecha de caducidad: "+cur.getString(3)+"\r\n"
                        +"Fecha de compra: "+cur.getString(4)+"\r\n"
                        +"Cantidad de producto almacenado: "+cur.getString(5)+"\r\n"
                        +"Path: "+cur.getString(6)+"\r\n";

                listData.add(item);
                item="";
            }while(cur.moveToNext());
        }

        return listData;
    }

    public ArrayList<String> getPROD(Cursor cur){
        ArrayList<String> listData = new ArrayList<String>();
        String item="";

        if(cur.moveToFirst()){
            do{
                item="ID del producto: ["+cur.getInt(0)+"]\r\n"
                        +"Nombre del producto: "+cur.getString(1)+"\r\n"
                        +"Cantidad a comprar : "+cur.getString(2)+"\r\n";

                listData.add(item);
                item="";
            }while(cur.moveToNext());
        }

        return listData;
    }

    public ArrayList<String> getA(Cursor cur){
        ArrayList<String> listData = new ArrayList<String>();
        String item="";

        if(cur.moveToFirst()){
            do{
                item="ID de la alarma: ["+cur.getInt(0)+"]\r\n"
                        +"Producto: "+cur.getString(1)+"\r\n"
                        +"Fecha: "+cur.getString(2)+"\r\n"
                        +"Hora : "+cur.getString(3)+"\r\n";

                listData.add(item);
                item="";
            }while(cur.moveToNext());
        }

        return listData;
    }


    public ArrayList<String> getID(Cursor cur){
        ArrayList<String> listData = new ArrayList<String>();
        String item ="";

        if(cur.moveToFirst()){
            do{
                item +="ID: ["+cur.getInt(0)+"]\r\n";
                item ="";
            }while (cur.moveToNext());
        }
        return listData;
    }


    public String addUpdatePer(int id, String nom, String org, String cumple, String sexo,
                               String tel, String foto){

        ContentValues cv = new ContentValues();

        cv.put("ID_PERSONA", id);
        cv.put("NOMBRE", nom);
        cv.put("ORGANIZACION", org);
        cv.put("CUMPLEANIOS", cumple);
        cv.put("SEXO", sexo);
        cv.put("TELEFONO", tel);
        cv.put("FOTO", foto);

        int cant = db.update("PERSONAS", cv, "ID_PERSONA=" + id, null);

        if(cant ==1){
            return "Usuario Mod";
        }else{
            return "Fallo algo";
        }

    }



    public Cursor getCant(int id){
        return db.rawQuery("SELECT * FROM PERSONAS WHERE ID_PERSONA = " + id,null);
    }

    public int Eliminar(Editable id){
        return db.delete("PERSONAS","ID_PERSONA =" +id,null);
    }

}
