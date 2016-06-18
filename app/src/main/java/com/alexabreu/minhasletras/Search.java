package com.alexabreu.minhasletras;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alexabreu.minhasletras.dao.LetraDAO;
import com.alexabreu.minhasletras.model.Letra;
import com.alexabreu.minhasletras.util.CustomAdapter;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {

    private Button btnPesquisar;
    private EditText edtPesquisar;
    private RadioGroup rgOpcao;
    private ListView listView;

    private Cursor cursor;
    private Letra letra;
    private LetraDAO dao;
    private ArrayList<Letra> lista = new ArrayList<Letra>();
    ArrayAdapter<Letra> adaptador = null;

    private static final String TAG = "search_letra";
    private CustomAdapter customAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        edtPesquisar = (EditText)findViewById(R.id.edt_busca);
        rgOpcao = (RadioGroup)findViewById(R.id.rg_opcao_busca);
        btnPesquisar = (Button)findViewById(R.id.btnBuscar);
        listView = (ListView)findViewById(R.id.listViewResultado);

        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               resultadoEscolha();
            }
        });

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

    public void resultadoEscolha(){

        if(edtPesquisar.length()== 0){
            AlertDialog.Builder mensagem = new AlertDialog.Builder(Search.this);
            mensagem.setTitle("Atenção");
            mensagem.setIcon(R.drawable.ic_warning_black_24dp);
            mensagem.setMessage("Por favor digite alguma coisa");
            mensagem.setCancelable(true);
            mensagem.setNeutralButton("OK", null);
            mensagem.show();
        } else {

            switch (rgOpcao.getCheckedRadioButtonId()) {
                case R.id.rb_nome_musica:
                    dao = new LetraDAO(this);
                    Letra letra = new Letra();
                    cursor = dao.getLetraListByNome(edtPesquisar.getText().toString());
                    Intent intent = new Intent(Search.this, ResultadoBusca.class);
                    Bundle b =  new Bundle();

                    if (cursor.moveToFirst()) {
                        do {
                            letra.setId_musica(cursor.getLong(cursor.getColumnIndex("_id_musica")));
                            letra.setNome_musica(cursor.getString(cursor.getColumnIndex("nome_musica")));
                            letra.setCantor_musica(cursor.getString(cursor.getColumnIndex("cantor_musica")));
                            letra.setLetra_musica(cursor.getString(cursor.getColumnIndex("letra_musica")));
                            lista.add(letra);

                            customAdapter = new CustomAdapter(Search.this, lista);
                            listView.setAdapter(customAdapter);
                            Log.i(TAG, "Pesquisa: " + letra.getNome_musica() + "(" + letra.getCantor_musica() + ")");
                        } while (cursor.moveToNext());



                        /*b.putSerializable("letras", lista);
                        intent.putExtras(b);
                        startActivity(intent);*/
                    }

                    break;
                case R.id.rb_nome_cantor:
                    Toast.makeText(Search.this, "Busca por nome do cantor!", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.rb_trecho:
                    Toast.makeText(Search.this, "Busca por trecho da música!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

}
