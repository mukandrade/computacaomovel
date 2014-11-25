package com.example.eduardo.escaradedecubitoapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.eduardo.escaradedecubitoapp.database.Database;
import com.example.eduardo.escaradedecubitoapp.database.CuidadorDAO;

import java.io.IOException;
import java.sql.SQLException;


public class TelaConfiguracoes_activity extends Activity {
    //Spinner spinnerTempoAlarme;
    Button btAplicar;
    EditText email,etTempo,etHoraSilencio,etCuidador;
    String nomePaciente,emailaux,nomeaux;
    CuidadorDAO cuidadorDAO;
    Cuidador cuidador;
    Database bd;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_configuracoes_activity);
        email = (EditText) findViewById(R.id.email);
        etCuidador = (EditText) findViewById(R.id.etCuidador);
        etTempo=(EditText) findViewById(R.id.etTempo2);
        etHoraSilencio = (EditText) findViewById(R.id.etHoraSilencio);
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.minutos_prompt,android.R.layout.simple_spinner_dropdown_item);
        //spinnerTempoAlarme.setAdapter(adapter);
        nomePaciente = getIntent().getStringExtra("nomePaciente");

        bd= Database.getInstance(getApplicationContext());

        try {
            bd.createBancodeDados();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bd.openBancodeDados();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        btAplicar = (Button) findViewById(R.id.btAplicar);

        btAplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int hrSilencio;
                    hrSilencio=Integer.parseInt(etHoraSilencio.getText().toString());
                    if(hrSilencio==0){
                        hrSilencio=24;
                    }
                        emailaux= email.getText().toString();
                        nomeaux= etCuidador.getText().toString();

                        Log.i("Eleementos"+" "+nomeaux+" ","Terminou");
                        cuidadorDAO=new CuidadorDAO(getApplicationContext());
                        cuidador=new Cuidador(null,nomeaux);
                        boolean teste=cuidadorDAO.insertCuidador(cuidador);
                        Log.i("teste"+teste,"");
                        Intent intent = new Intent(getApplicationContext(), TelaPaciente_activity.class);

                        intent.putExtra("HoraSilencio", hrSilencio);
                        intent.putExtra("tempo", Integer.parseInt(etTempo.getText().toString()));
                        intent.putExtra("email",emailaux);
                        intent.putExtra("nomeCuidador",nomeaux);
                        intent.putExtra("nomePac",nomePaciente);

                        startActivity(intent);
                        bd.close();
                }catch (Exception erro){
                    setMensagem("Erro ao salvar os dados","Erro");
                }
            }
        });

    }


    public void setMensagem(String msg,String titulo){
        AlertDialog.Builder mensagem = new AlertDialog.Builder(getApplicationContext());
        mensagem.setMessage(msg);
        mensagem.setTitle(titulo);
        mensagem.setNeutralButton("Ok",null);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            bd.close();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tela_configuracoes_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
