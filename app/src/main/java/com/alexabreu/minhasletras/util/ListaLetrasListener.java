package com.alexabreu.minhasletras.util;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.alexabreu.minhasletras.AddLetra;
import com.alexabreu.minhasletras.MainActivity;
import com.alexabreu.minhasletras.model.Letra;

/**
 * Created by alexd on 17/06/2016.
 */
public class ListaLetrasListener implements AdapterView.OnItemClickListener {

    MainActivity activity;

    public ListaLetrasListener(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(activity, AddLetra.class);
        i.putExtra("itemSelecionadoParaEdicao",(Letra)activity.getListaLetras().getItemAtPosition(position));
        activity.startActivity(i);
    }
}
