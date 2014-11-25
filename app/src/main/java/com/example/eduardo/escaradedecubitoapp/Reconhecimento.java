package com.example.eduardo.escaradedecubitoapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduardo on 03/10/2014.
 */
public class Reconhecimento {
    double x,y,z,t;



    public Reconhecimento(double x,double y, double z) {
        this.x=x;
        this.y=y;
        this.z=z;


    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }




    String reconhecimento (ArrayList dados) {
        if(mediaX(dados)<= -7.053364){
            return "Deitado para direita";
        }
        if(mediaX(dados)>-7.053364){
            if(mediaX(dados)<=6.6057){
                return "Deitado para cima";
            }
            if(mediaX(dados)>6.6057){
                return "Deitado para esquerda";
            }
        }
        return "Sem Classificação";
    }

    double mediaX (ArrayList dados) {
        int i = 0;
        ArrayList<Double> dados2 = new ArrayList<Double>();
        dados2=dados;
        double total = 0;
        for (i=0;i<dados2.size();i++){
            total = total+ dados2.get(i);
        }
        return (total/(dados2.size()));
    }

}