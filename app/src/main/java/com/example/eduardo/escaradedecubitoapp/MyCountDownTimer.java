package com.example.eduardo.escaradedecubitoapp;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Eduardo on 06/09/2014.
 */
public class MyCountDownTimer extends CountDownTimer {
    private TextView tv;
    private Context context;
    public long timerInFuture;
    long aux2=0;
    MediaPlayer mp;
    String aux,atividade;
    CheckBox cbAlertaSonoro;
    TextView etPosicao;
    char[] hora;
    int primeiroNumero=0,segundoNumero=0,horaSilencio;
    SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HH:mm:ss");




    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public MyCountDownTimer(Context context, TextView tv, CheckBox cbAlertaSonoro,int horaSilencio,String atividade, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.tv = tv;
        this.horaSilencio = horaSilencio;
        this.cbAlertaSonoro = cbAlertaSonoro;
        this.context = context;
        this.atividade=atividade;
        this.etPosicao=etPosicao;

    }

    @Override
    public void onTick(long millisUntilFinished) {
        timerInFuture = millisUntilFinished;
        if(millisUntilFinished<3600000) {
            tv.setText(getCorretTimer(true, millisUntilFinished) + ":" + getCorretTimer(false, millisUntilFinished));
        }
        if(millisUntilFinished>=3600000){
            //Log.i("TEMPO-->>", String.valueOf(millisUntilFinished));

            tv.setText(getCorretTimerH(true, (millisUntilFinished)) + ":" + getCorretTimerH(false, (millisUntilFinished)));
        }
    }

    @Override
    public void onFinish() {

        timerInFuture -= 1000;
        tv.setText(getCorretTimer(true, timerInFuture)+":"+getCorretTimer(false,timerInFuture));
        if(horaSilencio<10){
            horaSilencio=horaSilencio+12; //0-24H
        }
        if((cbAlertaSonoro.isChecked()==true)&& (horaAtual()<horaSilencio)) {

            tocarbip();
        }
    }

    public String getCorretTimer(boolean isMinute,long millisUntilFinished){

        int constCallendar = isMinute ? Calendar.MINUTE: Calendar.SECOND;
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millisUntilFinished);
        aux= c.get(constCallendar) < 10 ? "0" + c.get(constCallendar) : "" + c.get(constCallendar);
        return(aux);
    }
    public String getCorretTimerH(boolean isHour,long millisUntilFinished){
        int constCallendar = isHour ? Calendar.HOUR: Calendar.MINUTE;

        Log.i("HOUR", String.valueOf(millisUntilFinished));
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millisUntilFinished);

        if(isHour){
            if((millisUntilFinished/3600000)<10){
                aux="0"+((int)(millisUntilFinished/3600000));
                aux2=millisUntilFinished-3600000;
            }
            if((millisUntilFinished/3600000)>10){
                aux=""+((int)(millisUntilFinished/3600000));
                aux2=millisUntilFinished-3600000;
            }
        }
        if(!isHour){
            if(millisUntilFinished>=3600000) {

                if ((aux2 / 60000) < 10) {
                    aux = "0" + ((int) (aux2 / 60000));
                }
                if ((aux2 / 60000) > 10) {
                    aux = "" + ((int) (aux2 / 60000));
                }
            }
        }

        //aux= c.get(constCallendar) < 10 ? "0" + c.get(constCallendar) : "" + c.get(constCallendar);

        return (aux);
    }


    public void tocarbip() {
            if(atividade.equals("Deitado para esquerda")) {
                mp = MediaPlayer.create(context, R.raw.cimaoudireita);
                mp.start();
                mp.setLooping(true);

            }
            if (atividade.equals("Deitado para direita")){
                mp = MediaPlayer.create(context, R.raw.cimaouesquerda);
                mp.start();
                mp.setLooping(true);
            }
            if (atividade.equals("Deitado para cima")){
            mp = MediaPlayer.create(context, R.raw.viredireitaouesquerda);
            mp.start();
            mp.setLooping(true);
        }

        }

    public int horaAtual(){
        Date data = new Date();
        Calendar  cal = Calendar.getInstance();
        cal.setTime(data);
        int hora =cal.get(Calendar.HOUR);
        return hora;


    }


}


