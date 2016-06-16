package com.alexabreu.minhasletras.model;

import java.io.Serializable;

/**
 * Created by alexd on 08/06/2016.
 */
public class Letra  implements Serializable{

    // Labels table name
    public static final String TABLE = "Letra";

    // Labels Table Columns names
    public static final String KEY_ROWID = "_id";
    public static final String KEY_ID = "id";
    public static final String KEY_nome = "nome_musica";
    public static final String KEY_cantor = "nome_cantor";
    public static final String KEY_letra = "letra_musica";

    // property help us to keep data
    private Long letra_ID;
    private String nome_musica;
    private String nome_cantor;
    private String letra_musica;

    public Long getLetra_ID() {
        return letra_ID;
    }

    public void setLetra_ID(Long letra_ID) {
        this.letra_ID = letra_ID;
    }

    public String getNome_musica() {
        return nome_musica;
    }

    public void setNome_musica(String nome_musica) {
        this.nome_musica = nome_musica;
    }

    public String getNome_cantor() {
        return nome_cantor;
    }

    public void setNome_cantor(String nome_cantor) {
        this.nome_cantor = nome_cantor;
    }

    public String getLetra_musica() {
        return letra_musica;
    }

    public void setLetra_musica(String letra_musica) {
        this.letra_musica = letra_musica;
    }
}
