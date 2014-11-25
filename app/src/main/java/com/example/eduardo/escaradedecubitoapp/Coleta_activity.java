package com.example.eduardo.escaradedecubitoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eduardo.escaradedecubitoapp.database.ColetaDAO;
import com.example.eduardo.escaradedecubitoapp.database.Database;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Coleta_activity extends ActionBarActivity{
    Button btPararbip;
    TextView tvCronometro;
    CheckBox cbCheked;
    int aux=0,aux2=0,aux3=0,aux4=0,aux5=0,controle=0,temp,horaSilencio,iniciar;
    public MyCountDownTimer timer;
    static boolean executando = false;
    long inicial = 0,t,ti=0,taux=0;
    String nomePaciente,nomeCuidador,atividade="Deitado para cima",atividadeaux,email,body,setFrom,subject;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss.SSS");
    SensorManager sensorManager;
    SensorEventListener listener;
    public Reconhecimento reconhecimento;
    ImageView imagem;
    Calendar cal;
    Database bd;
    ColetaDAO coletaDAO;
    Coleta coleta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inicial = 0;
        setContentView(R.layout.activity_coleta_activity);
        imagem=(ImageView) findViewById(R.id.imagem);
        reconhecimento = new Reconhecimento(0,0,0);
        btPararbip = (Button) findViewById(R.id.btDesativar);
        tvCronometro = (TextView) findViewById(R.id.tvCronometro);
        cbCheked = (CheckBox) findViewById(R.id.cbAlertaSonoro);
        //Intents
        email =getIntent().getStringExtra("email");
        temp = getIntent().getIntExtra("tempo", 0);
        horaSilencio = getIntent().getIntExtra("horaSilencio", 0);
        iniciar = getIntent().getIntExtra("Iniciar",0);

        nomePaciente = getIntent().getStringExtra("nomePaciente");
        nomeCuidador = getIntent().getStringExtra("nomeCuidador");






        // imagens
        imagem.setImageResource(R.drawable.deitado_cima);
        bd= Database.getInstance(getApplicationContext());
        coletaDAO = new ColetaDAO(getApplicationContext());
        // abrindo o bd
        try {
            bd.createBancodeDados();
            bd.openBancodeDados();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //iniciando o contador regressivo
        if(iniciar==1){
            if(temp>0) {
                timer = new MyCountDownTimer(getApplicationContext(), tvCronometro, cbCheked, horaSilencio,atividade, (60000 * temp), 1000);//AQUI VAI ENTRAR O VALOR DO SPINNER
                timer.start();

            }
        }

        //COLETA DOS DADOS DO ACELEROMETRO
        if (!executando) {
            //notifica ao programa que esta sendo executada uma coleta
            executando=true;
            sensorManager = (SensorManager) getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
            Toast.makeText(getApplicationContext(),"Coleta Iniciada!", Toast.LENGTH_SHORT).show();
        }
        else{

        }
        //botão para parar o bip
        btPararbip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    timer.mp.stop();
                }catch (Exception erro){
                    // setMensagem("O alerme não foi disparado","Erro");
                }
            }

        });

        //AQUI COMEÇA RECONHECIMENTO

        //instancia o gerenciador de sensor
        sensorManager = (SensorManager) getApplicationContext().getSystemService(Context.SENSOR_SERVICE);

        //listener para o acelerometro
        listener = new SensorEventListener() {
            Dados coord = new Dados(0, 0, 0,"");
            @Override
            public void onSensorChanged(SensorEvent event) {
                //pega o horario relativo do novo evento
                Date data = new Date();
                cal = Calendar.getInstance();
                cal.setTime(data);

                ArrayList<Double> dados= new ArrayList<Double>();
                Date dataatual = cal.getTime();
                String datacompleta = dateFormat.format(dataatual);

                //pega as coordenadas do acelerometro e seta os valores no objeto coord
                coord.setX(event.values[SensorManager.DATA_X]);
                coord.setY(event.values[SensorManager.DATA_Y]);
                coord.setZ(event.values[SensorManager.DATA_Z]);
                coord.setTempo(datacompleta);
                dados.add(coord.getX());
                atividade=reconhecimento.reconhecimento(dados);

                if(atividade.equals("Deitado para cima")){
                    imagem.setImageResource(R.drawable.deitado_cima);
                }
                if(atividade.equals("Deitado para direita")){
                    imagem.setImageResource(R.drawable.deitado_direita);
                }
                if(atividade.equals("Deitado para esquerda")){
                    imagem.setImageResource(R.drawable.deitado_esquerda);
                }
                if(aux>1) {
                    verifica(atividade);
                }
                if(atividade.equals(atividadeaux)){

                    ti=cal.getTimeInMillis();

                        if ((ti - taux) > (2*(60000 * temp)) && (aux2 < 1) &&(aux5>0)) {
                            aux2++;
                            body = "Verificamos que o(a) paciente se encontra" + " " + (2 * temp) + " " + "minutos na mesma posição, recomendamos mudar a posição";
                            subject = "Recomendação de mudança na posição do paciente";
                            setFrom = "monitoramentopaciente@gmail.com";
                            EnviandoEmail task = new EnviandoEmail(getApplicationContext(), setFrom, subject, body);
                            task.execute(email, atividade);
                            controle++;
                            //Log.i("GRANDE DEMAIS!!!!!", "PASSOU O TEMPO11111111111111111");
                        }
                        if ((ti - taux) > (3*(60000 * temp)) && (aux3 < 1)) {
                            aux3++;
                            body = "Verificamos que o(a) paciente se encontra" + " " + (3 * temp) + " " + "minutos na mesma posição, recomendamos mudar a posição";
                            subject = "Recomendação de mudança na posição do paciente";
                            setFrom = "monitoramentopaciente@gmail.com";
                            EnviandoEmail task = new EnviandoEmail(getApplicationContext(), setFrom, subject, body);
                            task.execute(email, atividade);
                            controle++;
                            //Log.i("GRANDE DEMAIS!!!!!", "PASSOU O TEMPO222222222222222");
                        }
                        if ((ti - taux) > (4*(60000 * temp)) && (aux4 < 1)) {
                            aux4++;
                            body = "Verificamos que o(a) paciente se encontra" + " " + (4 * temp) + " " + "minutos na mesma posição, recomendamos mudar a posição";
                            subject = "Recomendação de mudança na posição do paciente";
                            setFrom = "monitoramentopaciente@gmail.com";
                            EnviandoEmail task = new EnviandoEmail(getApplicationContext(), setFrom, subject, body);
                            task.execute(email, atividade);
                            controle++;
                            //Log.i("GRANDE DEMAIS!!!!!", "PASSOU O TEMPO33333333333333333333333");
                        }

                }

                if(!atividade.equalsIgnoreCase(atividadeaux)) {//verifica se a posição mudou
                    ti=0;
                    aux++;
                    aux5++;
                    taux=cal.getTimeInMillis();

                    t=cal.getTimeInMillis()+20000; //QUANTIDADE DE TEMPO QUE TORNA VALIDA A MOVIMENTAÇÃO
                    atividadeaux = atividade;

                    //posicao.setText(atividade);

                }
            }


            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        //context=getApplicationContext();
        sensorManager.registerListener(listener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

        sensorManager.unregisterListener(listener);
        bd.close();
        super.onKeyDown(keyCode, event);
            try {
                timer.mp.stop();
            }catch (Exception e){

            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }



    public void verifica (String atividade2) { //FUNÇÃO PARA DETERMINAR SE RESETA OU NÃO O CRONOMETRO

        if (((cal.getTimeInMillis()) >= t) && (atividade2 == atividadeaux)) { //VERIFICA SE O TEMPO QUE FICOU NA NOVA POSIÇÃO É SUFICIENTE!

            String horario= cal.getTime().toString();
            String data = cal.getTime().toString();

            Log.i("Elmentos"+" "+nomePaciente+" "+atividade2+" "+horario+" "+(cal.getTimeInMillis()-(t-20000)),data+" "+nomeCuidador+" ");

            //inserindo no banco de dados
            coleta = new Coleta(null,nomePaciente,atividade2,horario,(cal.getTimeInMillis()-(t-20000)),data,nomeCuidador);
            coletaDAO.insertColeta(coleta);

            //ajustando o tempo e o contador
            aux = 0;
            atividadeaux = "";
            timer.cancel();
            timer = new MyCountDownTimer(getApplicationContext(), tvCronometro, cbCheked, horaSilencio,atividade2, ((60000 * temp) - 20000), 1000);//AQUI VAI ENTRAR O VALOR DO SPINNER
            timer.start();
            //ENVIA EMAIL PARA O CUIDADOR!!

            int SDK_INT = android.os.Build.VERSION.SDK_INT;

            if (SDK_INT > 8) {
                body="Verificamos uma mudança na posição do(a) paciente, agora ele(a) se encontra na posição";
                subject="Mudança de posição do(a) paciente!";
                setFrom="monitoramentopaciente@gmail.com";
                //EnviandoEmail enviandoEmail = new EnviandoEmail();
                EnviandoEmail task = new EnviandoEmail(getApplicationContext(),setFrom,subject,body);
                task.execute(email, atividade);
            }

        }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.coleta_activity, menu);
        return true;
    }
/*
    public static Toast geraToast(){
        Coleta_activity coleta_activity = new Coleta_activity();
        Context  context = coleta_activity.getApplicationContext();
        return Toast.makeText(context,"Email enviado com sucesso!",Toast.LENGTH_LONG);
    }*/

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
