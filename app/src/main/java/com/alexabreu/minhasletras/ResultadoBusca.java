package com.alexabreu.minhasletras;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alexabreu.minhasletras.model.Letra;
import com.alexabreu.minhasletras.util.CustomAdapter;

import java.util.ArrayList;
import java.util.Iterator;

public class ResultadoBusca extends AppCompatActivity {

    ListView lst_busca;
    String resultado;
    Cursor cursor;

    private static final String TAG = "resultado_letra";
    private CustomAdapter customAdapter;

    ArrayList<Letra> lista = new ArrayList<Letra>();
    ArrayAdapter<Letra> adaptador = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_busca);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        lst_busca = (ListView)findViewById(R.id.lst_resultado);

        lista = (ArrayList<Letra>) getIntent().getSerializableExtra("letras");
        adaptador = new ArrayAdapter<Letra>(this,android.R.layout.simple_list_item_1, lista);
        registerForContextMenu(lst_busca);
        lst_busca.setAdapter(adaptador);


        //lst_busca.setAdapter(customAdapter);
        //customAdapter = new CustomAdapter(ResultadoBusca.this, cursor, 0);
        /*Iterator it =  lista.iterator();
        while(it.hasNext()){
            Letra letra = (Letra)it.next();
            Log.i(TAG, "Resultado: "+ letra.getNome_musica());
        }*/

       /*for(int i=0; i<lista.size(); i++) {

           Log.i(TAG, "Resultado: "+lista.get(i).getNome_musica());
        }*/

        //Log.i(TAG, "Resultado: " + lista);

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
