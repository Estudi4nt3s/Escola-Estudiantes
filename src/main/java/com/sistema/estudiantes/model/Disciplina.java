package com.sistema.estudiantes.model;

public class Disciplina {
    private int id;
    private String nome;

    public Disciplina() {}

    public Disciplina(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Disciplina(String nome) {
        this.nome = nome;
    }

    public Disciplina(int id) {this.id = id;}

    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String toString() {
        return "Id: " + this.id + "\nNome: " + this.nome;
    }
}
