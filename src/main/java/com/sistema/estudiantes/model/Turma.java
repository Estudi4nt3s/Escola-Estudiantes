package com.sistema.estudiantes.model;

public class Turma extends ModelBase {
    private int ano;
    private String serie;
    private String curso;
    private char letra;

    public Turma() {}

    public Turma(int ano, String serie, String curso, char letra) {
        this.ano = ano;
        this.serie = serie;
        this.curso = curso;
        this.letra = letra;
    }

    public Turma(int id, int ano, String serie, String curso, char letra) {
        super(id);
        this.ano = ano;
        this.serie = serie;
        this.curso = curso;
        this.letra = letra;
    }

    public Turma(int id){
        int identifier = this.getId();
        identifier = id;
    }

    public int getAno() {
        return this.ano;
    }

    public String getSerie(){ return this.serie;}

    public String getCurso() {
        return this.curso;
    }

    public char getLetra() {
        return this.letra;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public void setLetra(char letra) {
        this.letra = letra;
    }

    public String toString() {
        return "Id: " + getId() + "\nAno: " + this.ano + "\nSérie:" + serie + "\nCurso: " + this.curso + "\nLetra: " + this.letra;
    }
}
