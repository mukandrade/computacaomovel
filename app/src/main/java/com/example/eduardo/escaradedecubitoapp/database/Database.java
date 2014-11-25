package com.example.eduardo.escaradedecubitoapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * Created by Eduardo on 03/10/2014.
 */
public final class Database extends SQLiteOpenHelper{

    private static Database instance;
    private static String DB_PATH = "/data/data/com.example.eduardo.escaradedecubitoapp/databases/";
    private static String DB_NAME = "EscaraAPPv5.db";
    public SQLiteDatabase databaseQuery;
    private final Context dbContext;


    public Database(Context context){

        super(context, DB_NAME, null, 1);
        this.dbContext = context;
    }

    public static Database getInstance(Context context) {

        if (instance == null) {
            instance = new Database(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteBancodeDados) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteBancodeDados, int i, int i2) {
        if(i2>i)
            try {
                copyBancodeDados();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    // ------------------------   CriaÃ§Ã£o e verificaÃ§Ã£o do banco de dados  -----------------------//

    public boolean checkBancodeDados()
    {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    public void createBancodeDados() throws IOException {

        boolean dbExist = checkBancodeDados();

        if (!dbExist) {

            this.getReadableDatabase();

            try {

                this.copyBancodeDados();

            } catch (IOException e) {
                throw new Error("Erro ao copiar o Banco de Dados!");
            }
        }
    }

    private void copyBancodeDados() throws IOException {

        InputStream myInput = dbContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;

        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openBancodeDados() throws SQLException {

        String myPath = DB_PATH + DB_NAME;
        databaseQuery = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READWRITE);

        databaseQuery.execSQL("PRAGMA busy_timeout=5000");
    }

    @Override
    public synchronized void close() {
        if (databaseQuery != null)
            databaseQuery.close();
        super.close();
    }
}