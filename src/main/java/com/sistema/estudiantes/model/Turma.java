package com.sistema.estudiantes.model;

public class Turma {
    private int id;
    private String nome;
    private int ano;
    private int quantidadeMax;

    public Turma() {}

    public Turma(int id, String nome, int ano, int quantidadeMax) {
        this.id = id;
        this.nome = nome;
        this.ano = ano;
        this.quantidadeMax = quantidadeMax;
    }

    public Turma(int id){
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public int getAno() {
        return this.ano;
    }

    public int getQuantidadeMax() {
        return this.quantidadeMax;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public void setQuantidadeMax(int quantidadeMax) {
        this.quantidadeMax = quantidadeMax;
    }


    public String toString() {
        return "Id: " + this.id + "\nNome: " + this.nome + "\nAno: " + this.ano + "\nQuantidade Máxima: " + this.quantidadeMax;
    }
}
