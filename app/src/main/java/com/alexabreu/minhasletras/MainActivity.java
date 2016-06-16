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
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private Long id_musica;
    private String nome_musica;
    private String nome_cantor;
    private String letra_musica;
    private String nomes_musica;

    private LetraDAO dao;

    int count = 0;
    int numero = 0;
    private ArrayList<Letra> listarTodos = new ArrayList<Letra>();

    private CustomAdapter customAdapter;
    private Letra letra_selecionada = null;
    private final static String TAG = MainActivity.class.getName().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);

        dao = new LetraDAO(this);
        listarTodos = dao.listarTodos();
        customAdapter = new CustomAdapter(this,listarTodos);

        listView = (ListView)findViewById(R.id.lstLetra);
        listView.setAdapter(customAdapter);

    }

    @Override
    public void onResume(){
        super.onResume();
       // atualizarLista();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case R.id.id_search:
                finish();
                return true;

            case R.id.id_adicionar:
                Intent intent = new Intent(this, AddLetra.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
