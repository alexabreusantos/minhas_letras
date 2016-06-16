package com.alexabreu.minhasletras;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alexabreu.minhasletras.dao.LetraDAO;
import com.alexabreu.minhasletras.model.Letra;

public class AddLetra extends AppCompatActivity {

    EditText nome_musica;
    EditText nome_cantor;
    EditText letra_musica;

    Long id;
    String nome;
    String cantor;
    String letra;

    Cursor cursor;
    LetraDAO dao;

    private final static String TAG = MainActivity.class.getName().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_letra);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        nome_musica = (EditText)findViewById(R.id.edt_nome_musica);
        nome_cantor = (EditText)findViewById(R.id.edt_nome_cantor);
        letra_musica = (EditText)findViewById(R.id.edt_letra_musica);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_letra, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;

            case R.id.id_save:
                save();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void save(){
        Letra letra = new Letra();
        LetraDAO letraDAO = new LetraDAO(this);

        try{
            letra.setNome_musica(nome_musica.getText().toString());
            letra.setNome_cantor(nome_cantor.getText().toString());
            letra.setLetra_musica(letra_musica.getText().toString());

            letraDAO.insert(letra);
            Toast.makeText(this, "Salvo com Sucesso", Toast.LENGTH_SHORT).show();
            finish();

        }catch (Exception ex){
            Log.i(TAG, "Erro", ex);
            Toast.makeText(this, "Erro ao Salvar", Toast.LENGTH_SHORT).show();
        }

    }
}