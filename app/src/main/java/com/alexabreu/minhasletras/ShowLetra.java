package com.alexabreu.minhasletras;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ZoomControls;

public class ShowLetra extends AppCompatActivity {

    private TextView nome_musica;
    private TextView letra_musica;
    private ImageButton aumentar;
    private ImageButton diminuir;

    private Long id;
    private String nome;
    private String cantor;
    private String letra;
    private  float tamanho_fonte = 18;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_letra);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ScrollView scrollable_contents = (ScrollView) findViewById(R.id.scrollableContents);
        getLayoutInflater().inflate(R.layout.contents_show_letra, scrollable_contents);

        nome_musica = (TextView) findViewById(R.id.txtNome);
        letra_musica = (TextView) findViewById(R.id.txtLetra);
        aumentar = (ImageButton) findViewById(R.id.imgAumentar);
        diminuir = (ImageButton) findViewById(R.id.imgDiminuir);

        aumentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tamanho_fonte++;
                letra_musica.setTextSize(tamanho_fonte);
            }
        });

        diminuir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tamanho_fonte--;
                letra_musica.setTextSize(tamanho_fonte);
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getLong("ID_SELECIONADO");
        nome = bundle.getString("NOME_SELECIONADO");
        cantor = bundle.getString("CANTOR_SELECIONADO");
        letra = bundle.getString("LETRA_SELECIONADA");

        nome_musica.setText(cantor);
        letra_musica.setText(letra);
        setTitle(nome);
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
