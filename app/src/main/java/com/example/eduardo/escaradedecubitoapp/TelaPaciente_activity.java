package com.example.eduardo.escaradedecubitoapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;







public class TelaPaciente_activity extends Activity {
    Button btPararbip,btIniciarContagem,btHistorico,btConfiguracoes;
    int temp,horaSilencio;
    TextView tvCronometro;
    CheckBox cbCheked;
    public MyCountDownTimer timer;
    String email;
    String nomeCuidador,nome;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_paciente_activity);
        btConfiguracoes = (Button) findViewById(R.id.btConfiguracoes);
        btIniciarContagem = (Button) findViewById(R.id.IniciarCronometro);
        btHistorico = (Button) findViewById(R.id.btHistorico);


        temp = getIntent().getIntExtra("tempo",0);
        horaSilencio = getIntent().getIntExtra("HoraSilencio",0);
        email = getIntent().getStringExtra("email");


        //GAMBIARRA KKKK PARA PASSAR O NOME DO PACIENTE
        if(getIntent().getStringExtra("nomePaciente")!= null) {
            nome = getIntent().getStringExtra("nomePaciente");
        }
        if(getIntent().getStringExtra("nomeCuidador")!= null) {
            nomeCuidador = getIntent().getStringExtra("nomeCuidador");
        }

        if(getIntent().getStringExtra("nomePac")!= null) {
            nome = getIntent().getStringExtra("nomePac");
        }

        Log.i("NomePacienteTelaPaciente:"+nome+" "," ");
        //FIM DA GAMBIARRA! KKKK


        btConfiguracoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent(getApplicationContext(),TelaConfiguracoes_activity.class);
                    intent.putExtra("nomePaciente",nome);
                    startActivity(intent);
                }catch (Exception erro){

                }
            }
        });

        btIniciarContagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent intent = new Intent(getApplicationContext(),Coleta_activity.class);

                    intent.putExtra("horaSilencio", horaSilencio);
                    intent.putExtra("email",email);
                    intent.putExtra("tempo",temp);
                    intent.putExtra("Iniciar",1);
                    intent.putExtra("nomePaciente",nome);
                    intent.putExtra("nomeCuidador",nomeCuidador);
                    Log.i("NomePacienteTelaPaciente:"+nome+" "," ");


                    startActivity(intent);

                }catch (Exception erro){
                    setMensagem("O tempo não foi passado para o contador regressivo, por favor acesse o botão configurações","Erro");
                }


            }

        });


       btHistorico.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),TelaHistorico_activity.class);
               startActivity(intent);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tela_paciente_activity, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_configuracoes) {
            Intent intent = new Intent(getApplicationContext(),TelaConfiguracoes_activity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
