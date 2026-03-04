package com.sistema.estudiantes.model;

public class Turma {
    private int id;
    private int ano;
    private String serie;
    private char letra;

    public Turma() {}

    public Turma(int id, int ano, String serie, char letra) {
        this.id = id;
        this.ano = ano;
        this.serie = serie;
        this.letra = letra;
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

    public String getSerie(){ return this.serie;}

    public char getLetra() {
        return this.letra;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public void setLetra(char letra) {
        this.letra = letra;
    }

    public String toString() {
        return "Id: " + this.id + "\nAno: " + this.ano + "\nSérie:" + serie + "\nLetra: " + this.letra;
    }
}
