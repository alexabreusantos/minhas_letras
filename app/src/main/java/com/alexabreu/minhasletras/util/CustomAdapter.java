package com.alexabreu.minhasletras.util;

import android.app.Activity;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alexabreu.minhasletras.R;
import com.alexabreu.minhasletras.model.Letra;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexd on 20/06/2016.
 */
public class CustomAdapter extends ArrayAdapter<Letra> {

    Activity context;
    ArrayList<Letra> Letras;
    private SparseBooleanArray mSelectedItemsIds;

    public CustomAdapter(Activity context, int resId, ArrayList<Letra> Letras) {
        super(context, resId, Letras);
        mSelectedItemsIds = new SparseBooleanArray();
        this.context = context;
        this.Letras = Letras;
    }

    private class ViewHolder {
        TextView id_musica;
        TextView nome_musica;
        TextView cantor_musica;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Letra letraPosicao = this.getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item, null);
            holder = new ViewHolder();
            holder.id_musica = (TextView) convertView.findViewById(R.id.txtId);
            holder.id_musica.setText(letraPosicao.getId_musica().toString());

            holder.nome_musica = (TextView) convertView.findViewById(R.id.txtNomeMusica);
            holder.nome_musica.setText(letraPosicao.getNome_musica());

            holder.cantor_musica = (TextView) convertView.findViewById(R.id.txtCantorMusica);
            holder.cantor_musica.setText(letraPosicao.getCantor_musica());
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Letra letra = getItem(position);
        holder.id_musica.setText(letra.getId_musica().toString());
        holder.nome_musica.setText(letra.getNome_musica());
        holder.cantor_musica.setText(letra.getCantor_musica());
        convertView.setBackgroundColor(mSelectedItemsIds.get(position) ? 0x9934B5E4 : Color.TRANSPARENT);
        return convertView;
    }

    @Override
    public void add(Letra letra) {
        Letras.add(letra);
        notifyDataSetChanged();
        Toast.makeText(context, Letras.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void remove(Letra object) {
        // super.remove(object);
        Letras.remove(object);
        notifyDataSetChanged();
    }

    public ArrayList<Letra> getLetras() {
        return Letras;
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);

        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

}
