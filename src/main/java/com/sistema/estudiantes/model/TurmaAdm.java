package com.sistema.estudiantes.model;

public class TurmaAdm {

    private int id;
    private String nome;
    private int ano;
    private int quantidadeAlunos;

    public TurmaAdm() {}

    public TurmaAdm(int id, String nome, int ano, int quantidadeAlunos) {
        this.id = id;
        this.nome = nome;
        this.ano = ano;
        this.quantidadeAlunos = quantidadeAlunos;
    }

    public TurmaAdm(int id) {
        this.id = id;
    }

    // GETTERS
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getAno() {
        return ano;
    }

    public int getQuantidadeAlunos() {
        return quantidadeAlunos;
    }

    // SETTERS
    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public void setQuantidadeAlunos(int quantidadeAlunos) {
        this.quantidadeAlunos = quantidadeAlunos;
    }
}