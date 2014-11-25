package com.example.eduardo.escaradedecubitoapp;

import java.util.Date;

/**
 * Created by Eduardo on 04/10/2014.
 */
public class Coleta {
    Integer id;
    long tempo_total;
    String nome_cuidador,nome_enfermo,estado,horario,data;

    public Coleta(Integer id,String nome_enfermo,String estado,String horario,long tempo_total,String data,String nome_cuidador) {
        this.id = id;
        this.tempo_total = tempo_total;
        this.nome_enfermo = nome_enfermo;
        this.estado = estado;
        this.horario = horario;
        this.data = data;
        this.nome_cuidador=nome_cuidador;
    }



    public String getNome_cuidador() {
        return nome_cuidador;
    }

    public String getNome_enfermo() {
        return nome_enfermo;
    }

    public String getData() {
        return data;
    }

    public Integer getId() {
        return id;
    }

    public Long getTempo_total() {
        return tempo_total;
    }


    public String getEstado() {
        return estado;
    }

    public String getHorario() {
        return horario;
    }
}
