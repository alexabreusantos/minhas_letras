package com.alexabreu.minhasletras;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

public class Search extends AppCompatActivity implements View.OnClickListener {

    private Button btnPesquisar;
    private EditText edtPesquisar;
    private RadioGroup rgOpcao;
    private ListView listView;
    private Resources resources;

    private Cursor cursor;
    private Letra letra;
    private LetraDAO dao;
    private ArrayList<Letra> lista = new ArrayList<Letra>();
    private ArrayAdapter<Letra> adaptador;

    private static final String TAG = "search_letra";
    private CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initViews();
        rgOpcao = (RadioGroup)findViewById(R.id.rg_opcao_busca);
        listView = (ListView)findViewById(R.id.listViewResultado);
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnBuscar) {
            if (validateFields()) {
                resultadoEscolha();
            }
        }
    }

    private void initViews() {
        resources = getResources();

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                callClearErrors(s);
            }
        };

        edtPesquisar = (EditText)findViewById(R.id.edt_busca);
        edtPesquisar.addTextChangedListener(textWatcher);
        btnPesquisar = (Button)findViewById(R.id.btnBuscar);
        btnPesquisar.setOnClickListener(this);
    }

    private void callClearErrors(Editable s) {
        if (!s.toString().isEmpty()) {
            clearErrorFields(edtPesquisar);
        }
    }

    private void clearErrorFields(EditText... editTexts) {
        for (EditText editText : editTexts) {
            editText.setError(null);
        }
    }

    private boolean validateFields() {
        String pesquisar = edtPesquisar.getText().toString().trim();
        return (!isEmptyFields(pesquisar));
    }

    private boolean isEmptyFields(String pesquisa) {
        if (TextUtils.isEmpty(pesquisa)) {
            edtPesquisar.requestFocus(); //seta o foco para o campo user
            edtPesquisar.setError(resources.getString(R.string.campo_obrigatorio));
            return true;
        }
        return false;
    }

    public void resultadoEscolha(){
        String pesquisa = edtPesquisar.getText().toString();
        if(pesquisa == null || pesquisa.equals("") || pesquisa.length() == 0 ){
            edtPesquisar.setError(getString(R.string.campo_obrigatorio));
        }
        else {
            switch (rgOpcao.getCheckedRadioButtonId()) {
                case R.id.rb_nome_musica:

                    dao = new LetraDAO(this);
                    Letra letra = new Letra();
                    this.lista = dao.buscarPorNomeMusica(pesquisa);

                    Intent intent = new Intent(Search.this, ResultadoBusca.class);
                    Bundle bundle =  new Bundle();
                    bundle.putSerializable("letras", lista);
                    intent.putExtras(bundle);
                    startActivity(intent);
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
