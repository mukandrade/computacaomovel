package com.example.eduardo.escaradedecubitoapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.eduardo.escaradedecubitoapp.Cuidador;

/**
 * Created by Eduardo on 03/10/2014.
 */
public class CuidadorDAO {
    private Database mDatabase;
    private SQLiteDatabase mDatabaseQuery;

    public static final String TABLE = "cuidador";
    public static final String ID = "_id";
    public static final String NOME = "nome_cuidador";




    public CuidadorDAO(Context context){

        this.mDatabase = Database.getInstance(context);
        this.mDatabaseQuery = mDatabase.getWritableDatabase();
    }

    public Cursor getCuidadorCursor(){

        Cursor cursor = mDatabaseQuery.rawQuery("select * from "+
                CuidadorDAO.TABLE+" order by "+CuidadorDAO.NOME, null);

        if(cursor!=null){

            cursor.moveToFirst();
        }

        return cursor;
    }

    public Cursor getCuidadorByIDCursor(int cuidadorID){

        String[] whereArgs = {Integer.toString(cuidadorID)};

        Cursor cursor = mDatabaseQuery.rawQuery("select * from "+
                CuidadorDAO.TABLE+" where "+CuidadorDAO.ID+" = ?", whereArgs);

        if(cursor!=null){

            cursor.moveToFirst();
        }

        return cursor;
    }

    public Cursor getNomesCursosr(){

        Cursor cursor = mDatabaseQuery.rawQuery("select "+CuidadorDAO.ID+","+CuidadorDAO.NOME+" from "+
                CuidadorDAO.TABLE+" order by "+CuidadorDAO.NOME, null);

        if(cursor!=null){

            cursor.moveToFirst();
        }

        return cursor;
    }


    public boolean insertCuidador(Cuidador cuidador) {

        ContentValues cv = new ContentValues();

        cv.put(CuidadorDAO.ID,cuidador.get_id());
        cv.put(CuidadorDAO.NOME, cuidador.getNome());
        //contentValues.put(CuidadorDAO.NOME, cuidador.getNome());
        //contentValues.put(CuidadorDAO.ID,cuidador.get_id());



        mDatabaseQuery.insert(CuidadorDAO.TABLE,null,cv);

        return true;
    }

    public boolean updateCuidador(Cuidador cuidador) {

        ContentValues cv = new ContentValues();

        cv.put(CuidadorDAO.ID,cuidador.get_id());
        cv.put(CuidadorDAO.NOME, cuidador.getNome());


        return true;
    }
}
