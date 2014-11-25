package com.example.eduardo.escaradedecubitoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;



public class TelaPrincipal_activity extends Activity {

    Button btMonitoramento,btRecomendacoes,btSobre,btCadastro;
    String nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal_activity);
        btRecomendacoes = (Button) findViewById(R.id.btRecomendacoes);
        btMonitoramento = (Button) findViewById(R.id.btMonitoramento);
        btCadastro = (Button) findViewById(R.id.btCadastroPaciente);

        nome= getIntent().getStringExtra("nomePaciente");

        btCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CadastroPaciente_activity.class);
                startActivity(intent);
            }
        });

        btRecomendacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRecom = new Intent(getApplicationContext(),TelaRecomendacoes_activity.class);
                startActivity(intentRecom);
            }
        });

        btMonitoramento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TelaPaciente_activity.class);
                Log.i("NomePacienteTelaPrincipal:"+nome+" "," ");
                intent.putExtra("nomePaciente",nome);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.tela_principal_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_sobre) {
            Intent intent = new Intent(getApplicationContext(),TelaSobre_activity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
