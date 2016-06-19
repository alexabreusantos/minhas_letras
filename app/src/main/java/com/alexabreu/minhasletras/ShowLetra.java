package com.alexabreu.minhasletras;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class ShowLetra extends AppCompatActivity {

    private TextView nome_musica;
    private TextView letra_musica;

    private Long id;
    private String nome;
    private String cantor;
    private String letra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_letra);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        nome_musica = (TextView) findViewById(R.id.txtNome);
        letra_musica = (TextView) findViewById(R.id.txtLetra);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getLong("ID_SELECIONADO");
        nome = bundle.getString("NOME_SELECIONADO");
        cantor = bundle.getString("CANTOR_SELECIONADO");
        letra = bundle.getString("LETRA_SELECIONADA");

        String nome_cantor = nome + " - " + cantor;
        nome_musica.setText(nome_cantor);
        letra_musica.setText(letra);

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
