package com.alexabreu.minhasletras.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.alexabreu.minhasletras.helper.DBHelper;
import com.alexabreu.minhasletras.model.Letra;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by alexd on 08/06/2016.
 */
public class LetraDAO {

    private DBHelper dbHelper;
    private Context context;
    private static final String TAG = "cadastro_letra";
    private static final String nome_tabela = "letras";

    public LetraDAO(Context context) {
        dbHelper = new DBHelper(context);
        this.context = context;
    }

    public void inserir(Letra letra) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome_musica", letra.getNome_musica());
        values.put("cantor_musica",letra.getCantor_musica());
        values.put("letra_musica", letra.getLetra_musica());

        // Inserting Row
        long id_letra = db.insert(nome_tabela, null, values);
        db.close(); // Closing database connection
    }

    public boolean atualizar(String id, String nome, String cantor, String letra){
        ContentValues values = new ContentValues();
        values.put("_id_musica", id);
        values.put("nome_musica", nome);
        values.put("cantor_musica",cantor);
        values.put("letra_musica", letra);

        SQLiteDatabase db =  dbHelper.getWritableDatabase();
        db.update(nome_tabela, values, "_id_musica=?", new String[]{id});
        db.close();

        return true;
    }

    public int remover(Long id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int valor_id = db.delete(nome_tabela, "_id_musica=?", new String[]{String.valueOf(id)});
        db.close();
        return valor_id;
    }

    public ArrayList<Letra> listarTodos() {
        ArrayList<Letra> letras = new ArrayList<Letra>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query(nome_tabela, null, null, null, null, null, "nome_musica ASC");

        try {
            while (c.moveToNext()){
                Letra letra = new Letra();
                letra.setId_musica(c.getLong(c.getColumnIndex("_id_musica")));
                letra.setNome_musica(c.getString(c.getColumnIndex("nome_musica")));
                letra.setCantor_musica(c.getString(c.getColumnIndex("cantor_musica")));
                letra.setLetra_musica(c.getString(c.getColumnIndex("letra_musica")));
                letras.add(letra);
            }
        }finally {
            c.close();
        }
        db.close();
        return letras;
    }

    public ArrayList<Letra> buscarPorNomeMusica(String search) {
        ArrayList<Letra> lista = new ArrayList<Letra>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT * FROM letras WHERE nome_musica LIKE '%" +search + "%' ";
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            while (cursor.moveToNext()){
                Letra letra = new Letra();
                letra.setId_musica(cursor.getLong(0));
                letra.setNome_musica(cursor.getString(1));
                letra.setCantor_musica(cursor.getString(2));
                letra.setLetra_musica(cursor.getString(3));
                lista.add(letra);
            }
        }finally {
            cursor.close();
        }
        db.close();
        return lista;
    }

    public ArrayList<Letra> buscarPorNomeCantor(String search) {
        ArrayList<Letra> lista = new ArrayList<Letra>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT * FROM letras WHERE cantor_musica LIKE '%" +search + "%' ";
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            while (cursor.moveToNext()){
                Letra letra = new Letra();
                letra.setId_musica(cursor.getLong(0));
                letra.setNome_musica(cursor.getString(1));
                letra.setCantor_musica(cursor.getString(2));
                letra.setLetra_musica(cursor.getString(3));
                lista.add(letra);
            }
        }finally {
            cursor.close();
        }
        db.close();
        return lista;
    }

    public ArrayList<Letra> buscarPorLetraMusica(String search) {
        ArrayList<Letra> lista = new ArrayList<Letra>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT * FROM letras WHERE letra_musica LIKE '%" +search + "%' ";
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            while (cursor.moveToNext()){
                Letra letra = new Letra();
                letra.setId_musica(cursor.getLong(0));
                letra.setNome_musica(cursor.getString(1));
                letra.setCantor_musica(cursor.getString(2));
                letra.setLetra_musica(cursor.getString(3));
                lista.add(letra);
            }
        }finally {
            cursor.close();
        }
        db.close();
        return lista;
    }

    public String obterNomeMusica(Letra letra){
        return letra.getNome_musica();
    }



}
