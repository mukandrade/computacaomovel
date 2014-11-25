package com.example.eduardo.escaradedecubitoapp;

/**
 * Created by Eduardo on 03/10/2014.
 */
public class Enfermo {
    String situacao,sexo,nome,idade;
    Integer _id;

    public Enfermo(Integer _id,String nome,String sexo,String situacao,String idade) {
        this.situacao = situacao;
        this.sexo = sexo;
        this.nome = nome;
        this.idade = idade;
        this._id = _id;
    }

    public String getSituacao() {
        return situacao;
    }

    public String getSexo() {
        return sexo;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return Integer.parseInt(idade);
    }

    public Integer get_id() {
        return _id;
    }
}
