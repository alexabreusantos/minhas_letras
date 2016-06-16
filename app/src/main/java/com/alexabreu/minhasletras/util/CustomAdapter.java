package com.alexabreu.minhasletras.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.alexabreu.minhasletras.R;
import com.alexabreu.minhasletras.model.Letra;

/**
 * Created by Tan on 3/14/2016.
 */

public class CustomAdapter extends CursorAdapter {

    private LayoutInflater mInflater;

    public CustomAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View   view    =    mInflater.inflate(R.layout.item, parent, false);
        ViewHolder holder  =   new ViewHolder();
        holder.txtId    =   (TextView)  view.findViewById(R.id.txtId);
        holder.txtNome    =   (TextView)  view.findViewById(R.id.txtNomeMusica);
        holder.txtCantor   =   (TextView)  view.findViewById(R.id.txtNomeCantor);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //If you want to have zebra lines color effect uncomment below code
        if(cursor.getPosition()%2==1) {
             view.setBackgroundResource(R.drawable.item_list_backgroundcolor);
        } else {
            view.setBackgroundResource(R.drawable.item_list_backgroundcolor2);
        }

        ViewHolder holder  =   (ViewHolder)    view.getTag();
        holder.txtId.setText(cursor.getString(cursor.getColumnIndex(Letra.KEY_ID)));
        holder.txtNome.setText(cursor.getString(cursor.getColumnIndex(Letra.KEY_nome)));
        holder.txtCantor.setText(cursor.getString(cursor.getColumnIndex(Letra.KEY_cantor)));
    }

    static class ViewHolder {
        TextView txtId;
        TextView txtNome;
        TextView txtCantor;
    }


}