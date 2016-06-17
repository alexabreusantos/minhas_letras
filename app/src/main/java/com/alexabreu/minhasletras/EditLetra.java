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

    private EditText nome_musica;
    private EditText nome_cantor;
    private EditText letra_musica;

    private Long musica_id;
    private String musica_nome;
    private String musica_cantor;
    private String musica_letra;
    private Letra letra;


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

        letra = (Letra)getIntent().getSerializableExtra("itemSelecionadoParaEdicao");
        musica_id = letra.getId_musica();
        musica_nome = letra.getNome_musica();
        musica_cantor = letra.getCantor_musica();
        musica_letra = letra.getLetra_musica();

        if(musica_id != null){
            nome_musica.setText(musica_nome);
            nome_cantor.setText(musica_cantor);
            letra_musica.setText(musica_letra);
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
                editar();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void editar(){
        LetraDAO dao = new LetraDAO(this);
        try{
            boolean isUpdate = dao.atualizar(musica_id.toString(),nome_musica.getText().toString(), nome_cantor.getText().toString(), letra_musica.getText().toString());
            Toast.makeText(this, "Editado com Sucesso", Toast.LENGTH_SHORT).show();
            finish();
        }catch (Exception ex){
            Log.i(TAG, "Erro", ex);
            Toast.makeText(this, "Erro ao Editar", Toast.LENGTH_SHORT).show();
        }
    }

}
