package com.alexabreu.minhasletras.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.alexabreu.minhasletras.model.Letra;

import java.util.Locale;

public class DBHelper  extends SQLiteOpenHelper {

    private static final int VERSAO_BANCO = 1;

    // Database Name
    private static final String NOME_BANCO = "minhas_letras.db";

    public DBHelper(Context context ) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_LETRA = "CREATE TABLE letras ("
                + "_id_musica INTEGER PRIMARY KEY AUTOINCREMENT, "
                +  "nome_musica TEXT, "
                + "cantor_musica TEXT, "
                + "letra_musica TEXT )";

        db.setLocale(Locale.getDefault());
        db.execSQL(CREATE_TABLE_LETRA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NOME_BANCO);
        onCreate(db);
    }

}