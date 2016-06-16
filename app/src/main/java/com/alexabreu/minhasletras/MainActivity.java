package com.alexabreu.minhasletras;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alexabreu.minhasletras.dao.LetraDAO;
import com.alexabreu.minhasletras.model.Letra;
import com.alexabreu.minhasletras.util.CustomAdapter;
import com.alexabreu.minhasletras.util.InserirLetra;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Cursor cursor;
    Cursor getCursor;
    LetraDAO dao;

    Long id_musica;
    String nome_musica;
    String nome_cantor;
    String letra_musica;
    String nomes_musica;

    int count = 0;
    int numero = 0;
    ArrayList<Letra> letras = new ArrayList<Letra>();

    private CustomAdapter customAdapter;
    private Letra letra_selecionada = null;
    private final static String TAG = MainActivity.class.getName().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);

    }


    @Override
    public void onResume(){
        super.onResume();
       // atualizarLista();
    }

}
