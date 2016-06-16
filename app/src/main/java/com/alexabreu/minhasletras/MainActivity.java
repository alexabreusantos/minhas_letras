package com.alexabreu.minhasletras;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alexabreu.minhasletras.dao.LetraDAO;
import com.alexabreu.minhasletras.model.Letra;
import com.alexabreu.minhasletras.util.CustomAdapter;
import com.alexabreu.minhasletras.util.InserirLetra;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Cursor cursor;
    Cursor getCursor;
    LetraDAO dao;

    Long id_musica;
    String nome_musica;
    String nome_cantor;
    String letra_musica;
    String nomes_musica;

    int count = 0;
    int numero = 0;
    ArrayList<Letra> letras = new ArrayList<Letra>();

    private CustomAdapter customAdapter;
    private Letra letra_selecionada = null;
    private final static String TAG = MainActivity.class.getName().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);

        dao = new LetraDAO(this);
        cursor = dao.getLetraList();

        customAdapter = new CustomAdapter(MainActivity.this,  cursor, 0);
        listView = (ListView) findViewById(R.id.lstLetra);
        registerForContextMenu(listView);
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);
                // Get the state's capital from this row in the database.
                id_musica = cursor.getLong(cursor.getColumnIndexOrThrow(Letra.KEY_ID));
                nome_musica = cursor.getString(cursor.getColumnIndexOrThrow(Letra.KEY_nome));
                nome_cantor = cursor.getString(cursor.getColumnIndexOrThrow(Letra.KEY_cantor));
                letra_musica = cursor.getString(cursor.getColumnIndexOrThrow(Letra.KEY_letra));

                Intent form = new Intent(MainActivity.this, ShowLetra.class);
                form.putExtra("ID_SELECIONADO", id_musica);
                form.putExtra("NOME_SELECIONADO", nome_musica);
                form.putExtra("CANTOR_SELECIONADO", nome_cantor);
                form.putExtra("LETRA_SELECIONADA", letra_musica);
                startActivity(form);
            }
        });

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                count = count+1;
                getCursor = (Cursor) listView.getItemAtPosition(position);

                id_musica = cursor.getLong(cursor.getColumnIndexOrThrow(Letra.KEY_ID));
                nome_musica = cursor.getString(cursor.getColumnIndexOrThrow(Letra.KEY_nome));
                nome_cantor = cursor.getString(cursor.getColumnIndexOrThrow(Letra.KEY_cantor));
                letra_musica = cursor.getString(cursor.getColumnIndexOrThrow(Letra.KEY_letra));

                if(count > 1){
                    mode.setTitle(count + " letras selecionadas");
                    nomes_musica = "Excluir as " + count + " letras selecionadas";
                    mode.getMenu().findItem(R.id.id_edit).setVisible(false);

                }else{
                    mode.setTitle(count + " letra selecionada");
                    nomes_musica = "Excluir a letra: " + cursor.getString(cursor.getColumnIndexOrThrow(Letra.KEY_nome));
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
                switch (item.getItemId()){
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

        if(cursor == null) insertLetra();
    }

   private void insertLetra(){
       InserirLetra inserirLetra = new InserirLetra(this);
       inserirLetra.addLetra();
    }

    @Override
    public void onResume(){
        super.onResume();
        atualizarLista();
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options_menu, menu);

        /*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView search = (SearchView) menu.findItem(R.id.id_search).getActionView();
            //search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String s) {
                    Log.d(TAG, "onQueryTextSubmit ");
                    cursor = dao.getLetraListByNome(s);
                    if (cursor==null){
                        Toast.makeText(MainActivity.this,"Nenhum registro encontrado",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(MainActivity.this, cursor.getCount() + " registros encontrado!",Toast.LENGTH_LONG).show();
                    }
                    customAdapter.swapCursor(cursor);
                    atualizarLista();

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    Log.d(TAG, "onQueryTextChange ");
                    cursor=dao.getLetraListByNome(s);
                    if (cursor!=null){
                       customAdapter.swapCursor(cursor);
                    }
                    return false;
                }

            });

        }*/
        return true;
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.id_adicionar:
                Intent add = new Intent(this, AddLetra.class);
                startActivity(add);
                break;
            case R.id.id_search:
                Intent search = new Intent(this, Search.class);
                startActivity(search);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void atualizarLista(){
        cursor = dao.getLetraList();
        customAdapter = new CustomAdapter(MainActivity.this,  cursor, 0);
        listView.setAdapter(customAdapter);
    }

    private void editarLetra() {
        Intent form = new Intent(MainActivity.this, EditLetra.class);
        form.putExtra("ID_SELECIONADO", id_musica);
        form.putExtra("NOME_SELECIONADO", nome_musica);
        form.putExtra("CANTOR_SELECIONADO", nome_cantor);
        form.putExtra("LETRA_SELECIONADA", letra_musica);
        startActivity(form);
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

                     dao.delet(id_musica);

                    //dao.delet(id_musica);

                   /* if(numero > 1){
                        Toast.makeText(MainActivity.this, "Letras excluídas com sucesso!", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(MainActivity.this, "Letra excluída com sucesso!", Toast.LENGTH_SHORT).show();

                    }*/

                    atualizarLista();
                    letra_selecionada = null;
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
