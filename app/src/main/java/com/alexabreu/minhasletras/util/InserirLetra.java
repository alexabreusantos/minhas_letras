package com.alexabreu.minhasletras.util;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.alexabreu.minhasletras.dao.LetraDAO;
import com.alexabreu.minhasletras.diversas_letras.AlineBarros;
import com.alexabreu.minhasletras.diversas_letras.DianteTrono;
import com.alexabreu.minhasletras.diversas_letras.FernandaBrum;
import com.alexabreu.minhasletras.diversas_letras.Fernandinho;
import com.alexabreu.minhasletras.model.Letra;

/**
 * Created by alexd on 08/06/2016.
 */
public class InserirLetra extends AppCompatActivity {
    String TAG = InserirLetra.class.getName().toString();
    LetraDAO dao;

    public InserirLetra(Context context) {
        dao = new LetraDAO(context);
    }

    public void addLetra(){
        Letra letra= new Letra();
       try{
           letra.setNome_musica(FernandaBrum.nomeEspiritoSanto);
           letra.setNome_cantor(FernandaBrum.cantorFernandaBrum);
           letra.setLetra_musica(FernandaBrum.letraEspiritoSanto);
           dao.insert(letra);

           letra.setNome_musica(FernandaBrum.nomeImpossivel);
           letra.setNome_cantor(FernandaBrum.cantorFernandaBrum);
           letra.setLetra_musica(FernandaBrum.letraImpossivel);
           dao.insert(letra);

           letra.setNome_musica(FernandaBrum.nomeApenasUmToque);
           letra.setNome_cantor(FernandaBrum.cantorFernandaBrum);
           letra.setLetra_musica(FernandaBrum.letraApenasUmToque);
           dao.insert(letra);

           letra.setNome_musica(AlineBarros.nomeCorpoFamilia);
           letra.setNome_cantor(AlineBarros.cantorAlineBarros);
           letra.setLetra_musica(AlineBarros.letraCorpoFamilia);
           dao.insert(letra);

           letra.setNome_musica(AlineBarros.nomeRendidoEstou);
           letra.setNome_cantor(AlineBarros.cantorAlineBarros);
           letra.setLetra_musica(AlineBarros.letraRendidoEstou);
           dao.insert(letra);

           letra.setNome_musica(AlineBarros.nomeRessuscitaMe);
           letra.setNome_cantor(AlineBarros.cantorAlineBarros);
           letra.setLetra_musica(AlineBarros.letraRessuscitaMe);
           dao.insert(letra);

           letra.setNome_musica(Fernandinho.nomeFazChover);
           letra.setNome_cantor(Fernandinho.cantorFernandinho);
           letra.setLetra_musica(Fernandinho.letraFazChover);
           dao.insert(letra);

           letra.setNome_musica(Fernandinho.nomeTeusSonhos);
           letra.setNome_cantor(Fernandinho.cantorFernandinho);
           letra.setLetra_musica(Fernandinho.letraTeusSonhos);
           dao.insert(letra);

           letra.setNome_musica(DianteTrono.nomeAclame);
           letra.setNome_cantor(DianteTrono.cantorDianteTrono);
           letra.setLetra_musica(DianteTrono.letraAclame);
           dao.insert(letra);

           letra.setNome_musica(DianteTrono.nomeAguasPurificadoras);
           letra.setNome_cantor(DianteTrono.cantorDianteTrono);
           letra.setLetra_musica(DianteTrono.letraAguasPurificadoras);
           dao.insert(letra);

           letra.setNome_musica(DianteTrono.nomePrecisoTi);
           letra.setNome_cantor(DianteTrono.cantorDianteTrono);
           letra.setLetra_musica(DianteTrono.letraPrecisoTi);
           dao.insert(letra);

           letra.setNome_musica(DianteTrono.nomeTeAgradeco);
           letra.setNome_cantor(DianteTrono.cantorDianteTrono);
           letra.setLetra_musica(DianteTrono.letraTeAgradeco);
           dao.insert(letra);

           letra.setNome_musica(DianteTrono.nomeVimParaAdorar);
           letra.setNome_cantor(DianteTrono.cantorDianteTrono);
           letra.setLetra_musica(DianteTrono.letraVimParaAdorar);
           dao.insert(letra);

       }catch (Exception ex){
           ex.getMessage();
       }


    }

}
