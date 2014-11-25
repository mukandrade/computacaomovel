package com.example.eduardo.escaradedecubitoapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.eduardo.escaradedecubitoapp.Coleta;

/**
 * Created by Eduardo on 04/10/2014.
 */
public class ColetaDAO {
    private Database mDatabase;
    private SQLiteDatabase mDatabaseQuery;

    public static final String TABLE = "coleta";
    public static final String ID = "_id";
    public static final String DATA = "data";
    public static final String NOME_ENFERMO = "nome_enfermo";
    public static final String NOME_CUIDADOR = "nome_cuidador";
    public static final String ESTADO = "estado";
    public static final String HORARIO = "horario";
    public static final String TEMPO_TOTAL = "tempo_total";



    public ColetaDAO(Context context){

        this.mDatabase = Database.getInstance(context);
        this.mDatabaseQuery = mDatabase.getWritableDatabase();
    }

    public Cursor getColetaCursor(){

        Cursor cursor = mDatabaseQuery.rawQuery("select * from "+
                CuidadorDAO.TABLE+" order by "+ColetaDAO.NOME_ENFERMO, null);

        if(cursor!=null){

            cursor.moveToFirst();
        }

        return cursor;
    }

    public Cursor getColetaByIDCursor(int cuidadorID){

        String[] whereArgs = {Integer.toString(cuidadorID)};

        Cursor cursor = mDatabaseQuery.rawQuery("select * from "+
                ColetaDAO.TABLE+" where "+ColetaDAO.ID+" = ?", whereArgs);

        if(cursor!=null){

            cursor.moveToFirst();
        }

        return cursor;
    }

    public Cursor getNomesCursosr(){

        Cursor cursor = mDatabaseQuery.rawQuery("select "+ColetaDAO.ID+","+ColetaDAO.NOME_ENFERMO+" from "+
                ColetaDAO.TABLE+" order by "+ColetaDAO.NOME_ENFERMO, null);

        if(cursor!=null){

            cursor.moveToFirst();
        }

        return cursor;
    }
    //PEGA AS COLETAS PELA DATA
    public Cursor getDataCursosr(){

        Cursor cursor = mDatabaseQuery.rawQuery("select "+ColetaDAO.ID+","+ColetaDAO.DATA+" from "+
                ColetaDAO.TABLE+" order by "+ColetaDAO.HORARIO, null);

        if(cursor!=null){

            cursor.moveToFirst();
        }

        return cursor;
    }

    public Cursor getCuidadorCursosr(){

        Cursor cursor = mDatabaseQuery.rawQuery("select "+ColetaDAO.ID+","+ColetaDAO.NOME_CUIDADOR+" from "+
                ColetaDAO.TABLE+" order by "+ColetaDAO.DATA, null);

        if(cursor!=null){

            cursor.moveToFirst();
        }

        return cursor;
    }


    public boolean insertColeta(Coleta coleta) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(ColetaDAO.ID, coleta.getId());
        contentValues.put(ColetaDAO.NOME_ENFERMO, coleta.getNome_enfermo());
        contentValues.put(ColetaDAO.ESTADO, coleta.getEstado());
        contentValues.put(ColetaDAO.HORARIO, coleta.getHorario());
        contentValues.put(ColetaDAO.TEMPO_TOTAL, coleta.getTempo_total());
        contentValues.put(ColetaDAO.DATA, coleta.getData());
        contentValues.put(ColetaDAO.NOME_CUIDADOR, coleta.getNome_cuidador());


        mDatabaseQuery.insert(ColetaDAO.TABLE,null,contentValues);

        return true;
    }

    public boolean updateColeta(Coleta coleta) {

        ContentValues contentValues = new ContentValues();


        contentValues.put(ColetaDAO.NOME_ENFERMO, coleta.getNome_enfermo());
        contentValues.put(ColetaDAO.ESTADO, coleta.getEstado());
        contentValues.put(ColetaDAO.HORARIO, coleta.getHorario());
        contentValues.put(ColetaDAO.TEMPO_TOTAL, coleta.getTempo_total());
        contentValues.put(ColetaDAO.DATA, coleta.getData());
        contentValues.put(ColetaDAO.NOME_CUIDADOR, coleta.getNome_cuidador());


        // String[] whereArgs = {Integer.toString(account.getId())};

        // databaseQuery.update(ColetaDAO.TABELA,contentValues, ColetaDAO.ID+" = ?", whereArgs);

        return true;
    }
}
