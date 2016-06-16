package com.alexabreu.minhasletras.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.alexabreu.minhasletras.helper.DBHelper;
import com.alexabreu.minhasletras.model.Letra;

/**
 * Created by alexd on 08/06/2016.
 */
public class LetraDAO {

    private DBHelper dbHelper;
    private static final String TAG = "cadastro_letra";
    private Context context;

    public LetraDAO(Context context) {
        dbHelper = new DBHelper(context);
        this.context = context;
    }

    public int insert(Letra letra) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome_musica", letra.getNome_musica());
        values.put(Letra.KEY_cantor,letra.getNome_cantor());
        values.put(Letra.KEY_letra, letra.getLetra_musica());

        // Inserting Row
        long letra_Id = db.insert(Letra.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) letra_Id;
    }

    public boolean update(String id, String nome, String cantor, String letra){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Letra.KEY_ID, id);
        values.put(Letra.KEY_nome, nome);
        values.put(Letra.KEY_cantor, cantor);
        values.put(Letra.KEY_letra, letra);

        db.update(Letra.TABLE, values, Letra.KEY_ID + "=?", new String[]{id});

        return true;
    }

    public int delet(Long id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows = db.delete(Letra.TABLE, "id = ?",new String[]{ String.valueOf(id) });
        return rows; // qtde. de linhas afetadas
    }

    public Cursor getLetraList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  rowid as " +
                Letra.KEY_ROWID + "," +
                Letra.KEY_ID + "," +
                Letra.KEY_nome + "," +
                Letra.KEY_cantor + "," +
                Letra.KEY_letra +
                " FROM " + Letra.TABLE + " ORDER BY " + Letra.KEY_nome;


        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }

    public Cursor getLetraListByNome(String search) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  rowid as " +
                Letra.KEY_ROWID + "," +
                Letra.KEY_ID + "," +
                Letra.KEY_nome + "," +
                Letra.KEY_cantor + "," +
                Letra.KEY_letra +
                " FROM " + Letra.TABLE +
                " WHERE " +  Letra.KEY_nome + "  LIKE  '%" +search + "%' ";
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }

    public Letra getLetraById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT " +
                Letra.KEY_ID + "," +
                Letra.KEY_nome + "," +
                Letra.KEY_cantor + "," +
                Letra.KEY_letra +
                " FROM " + Letra.TABLE
                + " WHERE " +
                Letra.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        Letra letra = new Letra();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                letra.setLetra_ID(cursor.getLong(cursor.getColumnIndex(Letra.KEY_ID)));
                letra.setNome_musica(cursor.getString(cursor.getColumnIndex(Letra.KEY_nome)));
                letra.setNome_cantor(cursor.getString(cursor.getColumnIndex(Letra.KEY_cantor)));
                letra.setLetra_musica(cursor.getString(cursor.getColumnIndex(Letra.KEY_letra)));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return letra;
    }

    public String obterNomeMusica(Cursor cursor){
        return cursor.getString(1);
    }
}
