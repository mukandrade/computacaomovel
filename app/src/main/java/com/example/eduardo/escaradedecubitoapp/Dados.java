package com.example.eduardo.escaradedecubitoapp;

/**
 * Created by Eduardo on 01/10/2014.
 */
public class Dados {
    double x,y,z;
    String tempo;
    public Dados(double x,double y,double z,String tempo) {
        this.x=x;
        this.y=y;
        this.z=z;
        this.tempo=tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public String getTempo() {
        return tempo;
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

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}
