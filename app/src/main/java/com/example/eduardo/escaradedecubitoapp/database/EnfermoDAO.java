package com.example.eduardo.escaradedecubitoapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.eduardo.escaradedecubitoapp.Enfermo;

/**
 * Created by Eduardo on 03/10/2014.
 */
public class EnfermoDAO {

    private Database mDatabase;
    private SQLiteDatabase mDatabaseQuery;

    public static final String TABLE = "enfermo";
    public static final String ID = "_id";
    public static final String NOME_ENFERMO = "nome_enfermo";
    public static final String SITUACAO = "situacao";
    public static final String SEXO = "sexo";
    public static final String IDADE = "idade";



    public EnfermoDAO(Context context){

        this.mDatabase = Database.getInstance(context);
        this.mDatabaseQuery = mDatabase.getWritableDatabase();
    }

    public Cursor getEnfermoCursor(){

        Cursor cursor = mDatabaseQuery.rawQuery("select * from "+
                CuidadorDAO.TABLE+" order by "+EnfermoDAO.NOME_ENFERMO, null);

        if(cursor!=null){

            cursor.moveToFirst();
        }

        return cursor;
    }

    public Cursor getEnfermoByIDCursor(int cuidadorID){

        String[] whereArgs = {Integer.toString(cuidadorID)};

        Cursor cursor = mDatabaseQuery.rawQuery("select * from "+
                EnfermoDAO.TABLE+" where "+EnfermoDAO.ID+" = ?", whereArgs);

        if(cursor!=null){

            cursor.moveToFirst();
        }

        return cursor;
    }

    public Cursor getNomesCursosr(){

        Cursor cursor = mDatabaseQuery.rawQuery("select "+EnfermoDAO.ID+","+EnfermoDAO.NOME_ENFERMO+" from "+
                EnfermoDAO.TABLE+" order by "+EnfermoDAO.NOME_ENFERMO, null);

        if(cursor!=null){

            cursor.moveToFirst();
        }

        return cursor;
    }

    public boolean insertEnfermo(Enfermo enfermo) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(EnfermoDAO.ID, enfermo.get_id());
        contentValues.put(EnfermoDAO.NOME_ENFERMO, enfermo.getNome());
        contentValues.put(EnfermoDAO.SEXO, enfermo.getSexo());
        contentValues.put(EnfermoDAO.SITUACAO, enfermo.getSituacao());
        contentValues.put(EnfermoDAO.IDADE, enfermo.getIdade());



        //        Log.i(enfermo.getNome()+enfermo.getSituacao()+ enfermo.getIdade(),"oi");

        mDatabaseQuery.insert(EnfermoDAO.TABLE,null,contentValues);

        return true;
    }

    public boolean updateEnfermo(Enfermo enfermo) {

        ContentValues contentValues = new ContentValues();


        contentValues.put(EnfermoDAO.NOME_ENFERMO, enfermo.getNome());
        contentValues.put(EnfermoDAO.SEXO, enfermo.getSexo());
        contentValues.put(EnfermoDAO.SITUACAO, enfermo.getSituacao());
        contentValues.put(EnfermoDAO.IDADE, enfermo.getIdade());


        // String[] whereArgs = {Integer.toString(account.getId())};

        // databaseQuery.update(EnfermoDAO.TABELA,contentValues, EnfermoDAO.ID+" = ?", whereArgs);

        return true;
    }
}
