package com.alexabreu.minhasletras;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alexabreu.minhasletras.dao.LetraDAO;
import com.alexabreu.minhasletras.model.Letra;
import com.alexabreu.minhasletras.util.InserirLetra;

import java.util.ArrayList;

public class SplashScreen extends Activity {

    private ProgressDialog pro_dialog;
    private Handler handler;
    private int MAX_PROGRESS;
    int progress;
    private LetraDAO letraDAO;
    private int tempo = 3500;
    InserirLetra inserirLetra = new InserirLetra(this);
    private ArrayList<Letra> letras = new ArrayList<Letra>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        instalarBanco();

        LetraDAO letraDAO = new LetraDAO(this);
        letras = letraDAO.listarTodos();
        MAX_PROGRESS = letras.size();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (progress >= MAX_PROGRESS) {
                    pro_dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Dados atualizados com sucesso", Toast.LENGTH_SHORT).show();
                } else {
                    progress++;
                    pro_dialog.incrementProgressBy(1);
                    handler.sendEmptyMessageDelayed(0, 100);
                }

            }
        };

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private void adicionarLetra(){
        inserirLetra.addLetra();
    }

    private void instalarBanco(){
        LetraDAO letraDAO = new LetraDAO(SplashScreen.this);
        letras = letraDAO.listarTodos();

        MAX_PROGRESS = letras.size();
        if(letras.isEmpty()){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Deseja atualizar os dados?");
            alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    MAX_PROGRESS = letras.size();
                    Log.i("TAG", "tamanho: "+ MAX_PROGRESS);
                    carregarBanco();
                    Thread timer = new Thread() {
                        public void run() {
                            try {
                                sleep(tempo);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } finally {
                                Intent openMainActivity = new Intent(SplashScreen.this, MainActivity.class);
                                startActivity(openMainActivity);
                            }
                        }
                    };
                    timer.start();
                }
            });

            alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    Toast.makeText(getApplicationContext(), "Nenhuma letra a ser exibida", Toast.LENGTH_SHORT).show();
                    Intent openMainActivity = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(openMainActivity);
                }
            });
            AlertDialog alertDialog = alert.create();
            alertDialog.setTitle("Minhas Letras");
            alertDialog.setIcon(R.mipmap.ic_launcher);
            alertDialog.show();
            adicionarLetra();

        }else {
            Intent openMainActivity = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(openMainActivity);
        }
    }

    private void carregarBanco(){
        pro_dialog = new ProgressDialog(SplashScreen.this);
        pro_dialog.setTitle("Aguarde...");
        pro_dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pro_dialog.setMax(MAX_PROGRESS);
        progress = 0;
        pro_dialog.show();
        pro_dialog.setProgress(0);
        handler.sendEmptyMessage(0);
    }
}
