package com.alexabreu.minhasletras.util;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.alexabreu.minhasletras.dao.LetraDAO;
import com.alexabreu.minhasletras.diversas_letras.AlineBarros;
import com.alexabreu.minhasletras.diversas_letras.DianteTrono;
import com.alexabreu.minhasletras.diversas_letras.FernandaBrum;
import com.alexabreu.minhasletras.diversas_letras.Fernandinho;
import com.alexabreu.minhasletras.model.Letra;

import java.util.ArrayList;

/**
 * Created by alexd on 08/06/2016.
 */
public class InserirLetra extends AppCompatActivity {
    String TAG = InserirLetra.class.getName().toString();
    LetraDAO dao;
    ArrayList<Letra> letras;

    public InserirLetra(Context context) {
        dao = new LetraDAO(context);
    }

    public void addLetra(){

        Letra letra= new Letra();

        letra.setNome_musica(FernandaBrum.nomeEspiritoSanto);
        letra.setCantor_musica(FernandaBrum.cantorFernandaBrum);
        letra.setLetra_musica(FernandaBrum.letraEspiritoSanto);
        dao.inserir(letra);

        letra.setNome_musica(FernandaBrum.nomeImpossivel);
        letra.setCantor_musica(FernandaBrum.cantorFernandaBrum);
        letra.setLetra_musica(FernandaBrum.letraImpossivel);
        dao.inserir(letra);

        letra.setNome_musica(FernandaBrum.nomeApenasUmToque);
        letra.setCantor_musica(FernandaBrum.cantorFernandaBrum);
        letra.setLetra_musica(FernandaBrum.letraApenasUmToque);
        dao.inserir(letra);

        letra.setNome_musica(AlineBarros.nomeCorpoFamilia);
        letra.setCantor_musica(AlineBarros.cantorAlineBarros);
        letra.setLetra_musica(AlineBarros.letraCorpoFamilia);
        dao.inserir(letra);

        letra.setNome_musica(AlineBarros.nomeRendidoEstou);
        letra.setCantor_musica(AlineBarros.cantorAlineBarros);
        letra.setLetra_musica(AlineBarros.letraRendidoEstou);
        dao.inserir(letra);

        letra.setNome_musica(AlineBarros.nomeRessuscitaMe);
        letra.setCantor_musica(AlineBarros.cantorAlineBarros);
        letra.setLetra_musica(AlineBarros.letraRessuscitaMe);
        dao.inserir(letra);

        letra.setNome_musica(Fernandinho.nomeFazChover);
        letra.setCantor_musica(Fernandinho.cantorFernandinho);
        letra.setLetra_musica(Fernandinho.letraFazChover);
        dao.inserir(letra);

        letra.setNome_musica(Fernandinho.nomeTeusSonhos);
        letra.setCantor_musica(Fernandinho.cantorFernandinho);
        letra.setLetra_musica(Fernandinho.letraTeusSonhos);
       // dao.inserir(letra);

        Letra aclame_dt = new Letra();
        aclame_dt.setNome_musica(DianteTrono.nomeAclame);
        aclame_dt.setCantor_musica(DianteTrono.cantorDianteTrono);
        aclame_dt.setLetra_musica(DianteTrono.letraAclame);
        dao.inserir(letra);

        letra.setNome_musica(DianteTrono.nomeAguasPurificadoras);
        letra.setCantor_musica(DianteTrono.cantorDianteTrono);
        letra.setLetra_musica(DianteTrono.letraAguasPurificadoras);
        dao.inserir(letra);

        letra.setNome_musica(DianteTrono.nomePrecisoTi);
        letra.setCantor_musica(DianteTrono.cantorDianteTrono);
        letra.setLetra_musica(DianteTrono.letraPrecisoTi);
        dao.inserir(letra);

        letra.setNome_musica(DianteTrono.nomeTeAgradeco);
        letra.setCantor_musica(DianteTrono.cantorDianteTrono);
        letra.setLetra_musica(DianteTrono.letraTeAgradeco);
        dao.inserir(letra);

        letra.setNome_musica(DianteTrono.nomeVimParaAdorar);
        letra.setCantor_musica(DianteTrono.cantorDianteTrono);
        letra.setLetra_musica(DianteTrono.letraVimParaAdorar);
        dao.inserir(letra);

    }
}
