package com.example.eduardo.escaradedecubitoapp;

import android.app.Activity;
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
import com.example.eduardo.escaradedecubitoapp.database.EnfermoDAO;

import java.io.IOException;
import java.sql.SQLException;


public class CadastroPaciente_activity extends Activity {
    String nome,sexo,situacao,idade;
    EditText etnome,etsexo,etsituacao,etidade;
    Button btSalvar;
    EnfermoDAO enfermoDAO;
    Enfermo enfermo;
    Database bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_paciente_activity);
        etnome=(EditText) findViewById(R.id.etNomeEnfermo);
        etsexo= (EditText) findViewById(R.id.etSexoEnfermo);
        etsituacao=(EditText) findViewById(R.id.etSituacao);
        etidade=(EditText) findViewById(R.id.etIdadeEnfermo);
        btSalvar=(Button) findViewById(R.id.btSalvar);
        bd= Database.getInstance(getApplicationContext());
        enfermoDAO = new EnfermoDAO(getApplicationContext());



        try {
            bd.createBancodeDados();
            bd.openBancodeDados();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TelaPrincipal_activity.class);

                nome = etnome.getText().toString();
                sexo = etsexo.getText().toString();
                situacao = etsituacao.getText().toString();
                idade = etidade.getText().toString();

                intent.putExtra("nomePaciente",nome);
                Log.i("Elementos" + " " + nome + " " + sexo + " " + situacao + " " + idade + " ", "Terminou");
                enfermo = new Enfermo(null,nome,sexo,situacao,idade);
                enfermoDAO.insertEnfermo(enfermo);
                startActivity(intent);
                bd.close();
            }
        });
        

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
        getMenuInflater().inflate(R.menu.cadastro_paciente_activity, menu);
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
