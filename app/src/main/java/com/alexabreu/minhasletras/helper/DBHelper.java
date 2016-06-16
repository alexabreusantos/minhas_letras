package com.alexabreu.minhasletras.helper;

/**
 * Created by Tan on 3/14/2016.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.alexabreu.minhasletras.model.Letra;

public class DBHelper  extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "letras.db";

    public DBHelper(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_LETRA = "CREATE TABLE " + Letra.TABLE  + "("
                + Letra.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Letra.KEY_nome + " TEXT, "
                + Letra.KEY_cantor + " TEXT, "
                + Letra.KEY_letra + " TEXT )";

        db.execSQL(CREATE_TABLE_LETRA);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + Letra.TABLE);

        // Create tables again
        onCreate(db);

    }

}