package com.example.eduardo.escaradedecubitoapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by Eduardo on 07/10/2014.
 */

public class EnviandoEmail extends AsyncTask<String,String,Void> {
    String email;
    String atividade;
    private Context context;
    Mail mail;
    Coleta_activity coleta;
    private ProgressDialog progressDialog;
    Coleta_activity coleta_activity;
    String setFrom,subject,body;
    public EnviandoEmail(Context context,String setFrom,String subject,String body) {
        this.body=body;
        this.setFrom=setFrom;
        this.subject=subject;
        this.context = context;
    }

        @Override
        public void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(context, "Enviando emai...", Toast.LENGTH_LONG);
           // progressDialog = ProgressDialog.show(coleta_activity,"Please wait", "Sending mail", true, false);
        }

    @Override
    public Void doInBackground(String... strings) {
        email = strings[0];
        atividade = strings[1];

        try {
            // Log.i("Email Enviado!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1","");
            mail = new Mail("monitoramentopaciente@gmail.com", "escaraapp");
            String[] toArr = {email};
            mail.setTo(toArr);
            mail.setFrom(setFrom);
            mail.setSubject(subject);
            mail.setBody(body + " " + atividade);
            mail.send();
            Toast.makeText(context,"Enviado com sucesso!! doIn",Toast.LENGTH_LONG);

        } catch (Exception e) {
//            e.printStackTrace();
        }

        return null;
    }

//bugado
    @Override
    public void onPostExecute(Void aVoid) {
      super.onPostExecute(aVoid);

        Toast.makeText(context, "Enviado com sucesso!!On post", Toast.LENGTH_LONG);
  //      progressDialog.dismiss();

    }


}

