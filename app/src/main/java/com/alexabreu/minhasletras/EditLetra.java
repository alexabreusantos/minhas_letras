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

import java.util.ArrayList;

public class EditLetra extends AppCompatActivity {

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

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getLong("ID_SELECIONADO");
        nome = bundle.getString("NOME_SELECIONADO");
        cantor = bundle.getString("CANTOR_SELECIONADO");
        letra = bundle.getString("LETRA_SELECIONADA");

        if(id != null){
            nome_musica.setText(nome);
            nome_cantor.setText(cantor);
            letra_musica.setText(letra);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_edit_letra, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;

            case R.id.id_edit:
                edit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void edit(){
        Letra letras = new Letra();
        LetraDAO dao = new LetraDAO(this);

        try{
            boolean isUpdate = dao.update(id.toString(), nome_musica.getText().toString(), nome_cantor.getText().toString(), letra_musica.getText().toString());
            Toast.makeText(this, "Editado com Sucesso", Toast.LENGTH_SHORT).show();
            finish();
        }catch (Exception ex){
            Log.i(TAG, "Erro", ex);
            Toast.makeText(this, "Erro ao Editar", Toast.LENGTH_SHORT).show();
        }

    }
}
