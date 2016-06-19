package com.alexabreu.minhasletras;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alexabreu.minhasletras.dao.LetraDAO;
import com.alexabreu.minhasletras.model.Letra;
import com.alexabreu.minhasletras.util.CustomAdapter;
import com.alexabreu.minhasletras.util.InserirLetra;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private Long id_musica;
    private String nome_musica;
    private String cantor_musica;
    private String letra_musica;
    private String nomes_musica;
    private Cursor cursor;
    private Letra letra;

    private LetraDAO dao;

    private ProgressDialog barProgressDialog;
    private Handler updateBarHandler;

    int tamanho_lista = 0;
    int count = 0;
    int numero = 0;

    private ArrayList<Letra> listarTodos = new ArrayList<Letra>();
    InserirLetra inserirLetra = new InserirLetra(this);

    private CustomAdapter customAdapter;
    private final static String TAG = MainActivity.class.getName().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);

        updateBarHandler = new Handler();

        dao = new LetraDAO(this);
        listarTodos = dao.listarTodos();

        if(listarTodos.isEmpty()){
            adicionarLetra();
        }

       // Log.i(TAG, "Tamanho da lista: " + inserirLetra.addLetra().size());

        customAdapter = new CustomAdapter(this,listarTodos);
        listView = (ListView)findViewById(R.id.lstLetra);
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                Letra letra = (Letra) listView.getItemAtPosition(position);

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
            }
        });

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                count = count + 1;
                letra = (Letra) listView.getItemAtPosition(position);

                id_musica = letra.getId_musica();
                nome_musica = letra.getNome_musica();
                cantor_musica = letra.getCantor_musica();
                letra_musica = letra.getLetra_musica();

                if (count > 1) {
                    mode.setTitle(count + " letras selecionadas");
                    nomes_musica = "Excluir as " + count + " letras selecionadas";
                    mode.getMenu().findItem(R.id.id_edit).setVisible(false);

                } else {
                    mode.setTitle(count + " letra selecionada");
                    nomes_musica = "Excluir a letra: " + letra.getNome_musica();
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.menu_contexto, menu);

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
                        mode.finish();
                        count = 0;
                        return true;

                    case R.id.id_edit:
                        editarLetra();
                        mode.finish();
                        count = 0;
                        return true;

                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                count = 0;
            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();
        carregarLista();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_search:
                Intent search = new Intent(this, Search.class);
                startActivity(search);
                return true;

            case R.id.id_adicionar:
                Intent add = new Intent(this, AddLetra.class);
                startActivity(add);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void adicionarLetra(){
        inserirLetra.addLetra();
        //ProgressBar();
    }

    public void ProgressBar() {
        barProgressDialog = new ProgressDialog(MainActivity.this);

        barProgressDialog.setTitle("Atualizando os dados...");
        barProgressDialog.setMessage("Atualização em progresso...");
        barProgressDialog.setProgressStyle(barProgressDialog.STYLE_HORIZONTAL);
        barProgressDialog.setProgress(0);
        barProgressDialog.setMax(5);
        barProgressDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    // Here you should write your time consuming task...
                    while (barProgressDialog.getProgress() <= barProgressDialog.getMax()) {

                        Thread.sleep(1500);

                        updateBarHandler.post(new Runnable() {

                            public void run() {

                                barProgressDialog.incrementProgressBy(2);

                            }

                        });

                        if (barProgressDialog.getProgress() == barProgressDialog.getMax()) {

                            barProgressDialog.dismiss();
                        }
                    }
                } catch (Exception e) {
                }
            }
        }).start();
    }

    public void carregarLista(){
        LetraDAO letraDAO = new LetraDAO(this);
        this.listarTodos = letraDAO.listarTodos();
        this.customAdapter = new CustomAdapter(this, listarTodos);
        this.listView.setAdapter(customAdapter);
    }

    private void editarLetra() {
        Intent i = new Intent(this, EditLetra.class);
        i.putExtra("itemSelecionadoParaEdicao",letra);
        this.startActivity(i);
    }

    private void confirmarExcluir(){

        try{

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage(nomes_musica + "?");

            alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    LetraDAO dao = new LetraDAO(MainActivity.this);
                    numero = numero+1;

                    dao.remover(id_musica);

                    //dao.delet(id_musica);

                   /* if(numero > 1){
                        Toast.makeText(MainActivity.this, "Letras excluídas com sucesso!", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(MainActivity.this, "Letra excluída com sucesso!", Toast.LENGTH_SHORT).show();

                    }*/

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

    public ListView getListaLetras() {
        return listView;
    }
}
