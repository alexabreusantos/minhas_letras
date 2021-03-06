package com.alexabreu.minhasletras;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.content.Intent;
import android.database.SQLException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alexabreu.minhasletras.dao.LetraDAO;
import com.alexabreu.minhasletras.diversas_letras.DianteTrono;
import com.alexabreu.minhasletras.diversas_letras.FernandaBrum;
import com.alexabreu.minhasletras.model.Letra;
import com.alexabreu.minhasletras.util.CustomAdapter;
import com.alexabreu.minhasletras.util.InserirLetra;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView letraListView;
    private ProgressDialog progressDialog;
    private Handler handler;
    private CustomAdapter customAdapter;
    private Toast toast;
    private long lastBackPressTime;

    private ArrayList<Letra> letras = new ArrayList<Letra>();
    private int qtd_letras;
    private ArrayList<Long> ids = new ArrayList<Long>();

    private Long id_musica;
    private String nome_musica;
    private String letras_selecionadas;
    private String cantor_musica;
    private String letra_musica;

    private ActionMode mActionMode;
    private LetraDAO dao;
    private Letra letra;
    InserirLetra inserirLetra = new InserirLetra(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        letraListView = (ListView) findViewById(R.id.lstLetra);
        handler = new Handler();

        dao = new LetraDAO(this);
        letras = dao.listarTodos();

        carregarLista();
        customAdapter = new CustomAdapter(this, R.layout.item,letras);
        letraListView.setAdapter(customAdapter);
        letraListView.setOnItemClickListener(this);
        letraListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                onListItemSelect(position);
                return true;
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        carregarLista();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (mActionMode == null) {
            Letra letra = (Letra) letraListView.getItemAtPosition(position);
            // Get the state's capital from this row in the database.
            id_musica = letra.getId_musica();
            nome_musica = letra.getNome_musica();
            cantor_musica = letra.getCantor_musica();
            letra_musica = letra.getLetra_musica();

            Intent form = new Intent(MainActivity.this, ShowLetra.class);
            form.putExtra("ID_SELECIONADO", id_musica);
            form.putExtra("NOME_SELECIONADO", nome_musica);
            form.putExtra("CANTOR_SELECIONADO", cantor_musica);
            form.putExtra("LETRA_SELECIONADA", letra_musica);
            startActivity(form);

        } else
            // add or remove selection for current list item
            onListItemSelect(position);
    }

    @Override
    public void onBackPressed() {
        if (this.lastBackPressTime < System.currentTimeMillis() - 4000) {
            toast = Toast.makeText(this, "Pressione voltar novamente para sair", Toast.LENGTH_LONG);
            toast.show();
            this.lastBackPressTime = System.currentTimeMillis();
        } else {
            if (toast != null) {
                toast.cancel();
            }
            super.onBackPressed();
        }
    }
    private void onListItemSelect(int position) {
        customAdapter.toggleSelection(position);
        boolean hasCheckedItems = customAdapter.getSelectedCount() > 0;

        letra = (Letra) letraListView.getItemAtPosition(position);

        id_musica = letra.getId_musica();
        nome_musica = letra.getNome_musica();
        cantor_musica = letra.getCantor_musica();
        letra_musica = letra.getLetra_musica();

        ids.add(id_musica);

        if (hasCheckedItems && mActionMode == null)
            // there are some selected items, start the actionMode
            mActionMode = startActionMode(new ActionModeCallback());
        else if (!hasCheckedItems && mActionMode != null)
            // there no selected items, finish the actionMode
            mActionMode.finish();

        if (mActionMode != null)
            mActionMode.setTitle(String.valueOf(customAdapter.getSelectedCount()) + " selecionado(s)");

        if(customAdapter.getSelectedCount() > 1){
            letras_selecionadas = "Excluir as " + customAdapter.getSelectedCount() + " letras selecionadas?";
            mActionMode.getMenu().findItem(R.id.id_edit).setVisible(false);

        }else{
            letras_selecionadas = "Excluir a letra: " + letra.getNome_musica() + "?";
        }

    }

    private void carregarLista() {
        LetraDAO letraDAO = new LetraDAO(this);
        this.letras = letraDAO.listarTodos();
        this.customAdapter = new CustomAdapter(this,R.layout.item, letras);
        this.letraListView.setAdapter(customAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.id_add) {
            Intent add = new Intent(this, AddLetra.class);
            startActivity(add);
        } else if (item.getItemId() == R.id.id_search) {
            Intent search = new Intent(this, Search.class);
            startActivity(search);
        } else if (item.getItemId() == R.id.id_favorits) {
            // add rate feature to your application by launching market
        }
        return true;
    }

    private class ActionModeCallback implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // inflate contextual menu
            mode.getMenuInflater().inflate(R.menu.menu_contexto, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            switch (item.getItemId()) {
                case R.id.id_delete:
                    confirmarExcluir();
                    mode.finish(); // Action picked, so close the CAB
                    return true;

                case R.id.id_edit:
                    editarLetra();
                    mode.finish();
                    return true;

                default:
                    return false;
            }

        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            // remove selection
            customAdapter.removeSelection();
            mActionMode = null;
        }
    }

    private void editarLetra() {
        Intent i = new Intent(this, EditLetra.class);
        i.putExtra("itemSelecionadoParaEdicao",letra);
        this.startActivity(i);
    }

    private void confirmarExcluir(){

        try{

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage(letras_selecionadas);

            alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    LetraDAO dao = new LetraDAO(MainActivity.this);

                    for(int i=0;i<ids.size();i++){
                        dao.remover(ids.get(i));
                        Log.i("TAG", "selecionado: " + ids.get(i));
                    }
                    ids.clear();
                    carregarLista();
                }
            });

            alert.setNegativeButton("Não", null);
            AlertDialog alertDialog = alert.create();
            alertDialog.setTitle("Confirmação de Exclusão!");
            alertDialog.setIcon(R.drawable.ic_delete_forever_black_24dp);
            alertDialog.show();

        }catch (SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro ao excluir " + ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
    }
}

