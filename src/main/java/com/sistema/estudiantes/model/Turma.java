package com.sistema.estudiantes.model;

public class Turma {
    private int id;
    private int ano;
    private String nome;

    public Turma() {}

    public Turma(int id, int ano, String nome) {
        this.id = id;
        this.ano = ano;
        this.nome = nome;
    }

    public Turma(int id){
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public int getAno() {
        return this.ano;
    }

    public String getNome(){ return this.nome;}

    public void setId(int id) {
        this.id = id;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String toString() {
        return "Id: " + this.id + "\nAno: " + this.ano + "\nSérie:" + nome;
    }
}
