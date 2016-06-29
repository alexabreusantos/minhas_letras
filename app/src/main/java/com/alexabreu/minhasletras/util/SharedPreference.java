package com.alexabreu.minhasletras.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.alexabreu.minhasletras.model.Letra;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by alexd on 29/06/2016.
 */
public class SharedPreference {

    public static final String PREFS_NAME = "LETRA_APP";
    public static final String FAVORITES = "Letras_favoritas";

    public SharedPreference() {
        super();
    }

    // This four methods are used for maintaining favorites.
    public void saveFavorites(Context context, ArrayList<Letra> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }

    public void addFavorite(Context context, Letra letra) {
        ArrayList<Letra> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<Letra>();
        favorites.add(letra);
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, Letra letra) {
        ArrayList<Letra> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(letra);
            saveFavorites(context, favorites);
        }
    }

    public ArrayList<Letra> getFavorites(Context context) {
        SharedPreferences settings;
        List<Letra> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            Letra[] favoriteItems = gson.fromJson(jsonFavorites,Letra[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<Letra>(favorites);
        } else
            return null;

        return (ArrayList<Letra>) favorites;
    }
}