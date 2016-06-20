package com.alexabreu.minhasletras.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.alexabreu.minhasletras.R;
import com.alexabreu.minhasletras.model.Letra;

import java.util.ArrayList;

/**
 * Created by Tan on 3/14/2016.
 */

public class CustomAdapter extends ArrayAdapter<Letra> {

    private LayoutInflater mInflater;
    private Context context;
    private ArrayList<Letra> letras;

    private TextView id_musica;
    private TextView nome_musica;
    private TextView cantor_musica;

    public CustomAdapter(Context context, ArrayList<Letra> lista) {
        super(context, 0, lista);
        this.context = context;
        this.letras = lista;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Letra letraPosicao = this.letras.get(position);
        convertView = LayoutInflater.from(this.context).inflate(R.layout.item, null);

        id_musica = (TextView)convertView.findViewById(R.id.txtId);
        id_musica.setText(letraPosicao.getId_musica().toString());

        nome_musica = (TextView)convertView.findViewById(R.id.txtNomeMusica);
        nome_musica.setText(letraPosicao.getNome_musica());

        cantor_musica = (TextView)convertView.findViewById(R.id.txtCantorMusica);
        cantor_musica.setText(letraPosicao.getCantor_musica());

        if(position %2==1){
            convertView.setBackgroundResource(R.drawable.item_list_backgroundcolor);
        }
        else {
            convertView.setBackgroundResource(R.drawable.item_list_backgroundcolor2);
        }

        return convertView;
    }
}