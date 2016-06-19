package com.alexabreu.minhasletras;

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
    private Resources resources;

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
        initViews();

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
                if (validateFields()) {
                    editar();
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
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
        nome_musica = (EditText)findViewById(R.id.edt_nome_musica);
        nome_musica.addTextChangedListener(textWatcher);
        nome_cantor = (EditText)findViewById(R.id.edt_nome_cantor);
        nome_cantor.addTextChangedListener(textWatcher);
        letra_musica = (EditText)findViewById(R.id.edt_letra_musica);
        letra_musica.addTextChangedListener(textWatcher);
    }

    /**
     * Chama o método para limpar erros
     *
     * @param s Editable
     */
    private void callClearErrors(Editable s) {
        if (!s.toString().isEmpty()) {
            clearErrorFields(nome_musica);
        }
    }
    /**
     * Efetua a validação dos campos.Nesse caso, valida se os campos não estão vazios e se tem
     * tamanho permitido.
     * Nesse método você poderia colocar outros tipos de validações de acordo com a sua necessidade.
     *
     * @return boolean que indica se os campos foram validados com sucesso ou não
     */
    private boolean validateFields() {
        String musica_nome = nome_musica.getText().toString().trim();
        String musica_cantor = nome_cantor.getText().toString().trim();
        String musica_letra = letra_musica.getText().toString().trim();
        return (!isEmptyFields(musica_nome, musica_cantor, musica_letra));
    }

    private boolean isEmptyFields(String musica_nome, String musica_cantor, String musica_letra) {
        if (TextUtils.isEmpty(musica_nome)) {
            nome_musica.requestFocus(); //seta o foco para o campo nome da musica
            nome_musica.setError(resources.getString(R.string.campo_obrigatorio));
            return true;
        } else if (TextUtils.isEmpty(musica_cantor)) {
            nome_cantor.requestFocus(); //seta o foco para o campo nome do cantor
            nome_cantor.setError(resources.getString(R.string.campo_obrigatorio));
            return true;
        }else if (TextUtils.isEmpty(musica_letra)) {
            letra_musica.requestFocus(); //seta o foco para o campo letra da musica
            letra_musica.setError(resources.getString(R.string.campo_obrigatorio));
            return true;
        }
        return false;
    }
    /**
     * Limpa os ícones e as mensagens de erro dos campos desejados
     *
     * @param editTexts lista de campos do tipo EditText
     */
    private void clearErrorFields(EditText... editTexts) {
        for (EditText editText : editTexts) {
            editText.setError(null);
        }
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
