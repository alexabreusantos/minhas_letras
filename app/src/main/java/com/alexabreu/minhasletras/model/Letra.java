package com.alexabreu.minhasletras.model;

import java.io.Serializable;

/**
 * Created by alexd on 08/06/2016.
 */
public class Letra  implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long id_musica;
    private String nome_musica;
    private String cantor_musica;
    private String letra_musica;

    public Long getId_musica() {
        return id_musica;
    }

    public void setId_musica(Long id_musica) {
        this.id_musica = id_musica;
    }

    public String getNome_musica() {
        return nome_musica;
    }

    public void setNome_musica(String nome_musica) {
        this.nome_musica = nome_musica;
    }

    public String getCantor_musica() {
        return cantor_musica;
    }

    public void setCantor_musica(String cantor_musica) {
        this.cantor_musica = cantor_musica;
    }

    public String getLetra_musica() {
        return letra_musica;
    }

    public void setLetra_musica(String letra_musica) {
        this.letra_musica = letra_musica;
    }
}
