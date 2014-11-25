package com.example.eduardo.escaradedecubitoapp;

/**
 * Created by Eduardo on 03/10/2014.
 */
public class Cuidador {
    String nome;
    Integer _id;
    public Cuidador(Integer _id,String nome) {
        this._id=_id;
        this.nome=nome;
    }

    public String getNome() {
        return nome;
    }


    public Integer get_id() {
        return _id;
    }
}
