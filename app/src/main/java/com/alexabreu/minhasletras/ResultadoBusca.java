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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alexabreu.minhasletras.model.Letra;
import com.alexabreu.minhasletras.util.CustomAdapter;

import java.util.ArrayList;
import java.util.Iterator;

public class ResultadoBusca extends AppCompatActivity {

    private ListView lst_busca;

    private Long id_musica;
    private String nome_musica;
    private String cantor_musica;
    private String letra_musica;

    private CustomAdapter customAdapter;
    private static final String TAG = "resultado_letra";
    private ArrayList<Letra> lista = new ArrayList<Letra>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_busca);

        setTitle("Resultado da Pesquisa");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        lst_busca = (ListView)findViewById(R.id.lst_resultado);

        lista = (ArrayList<Letra>) getIntent().getSerializableExtra("letras");
        customAdapter = new CustomAdapter(this,R.layout.item_resultado,lista);
        lst_busca.setAdapter(customAdapter);
        lst_busca.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                Letra letra = (Letra) lst_busca.getItemAtPosition(position);

                // Get the state's capital from this row in the database.
                id_musica = letra.getId_musica();
                nome_musica = letra.getNome_musica();
                cantor_musica = letra.getCantor_musica();
                letra_musica = letra.getLetra_musica();

                Intent form = new Intent(ResultadoBusca.this, ShowLetra.class);
                form.putExtra("ID_SELECIONADO", id_musica);
                form.putExtra("NOME_SELECIONADO", nome_musica);
                form.putExtra("CANTOR_SELECIONADO", cantor_musica);
                form.putExtra("LETRA_SELECIONADA", letra_musica);
                startActivity(form);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_resultado_letra, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
