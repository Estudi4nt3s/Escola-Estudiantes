package com.sistema.estudiantes.model;

public class Disciplina extends ModelBase{
    private int id;
    private String nome;

    public Disciplina() {}

    public Disciplina(String nome) {
        this.nome = nome;
    }

    public Disciplina(int id, String nome) {
        super(id);
        this.nome = nome;
    }

    public Disciplina(int id){this.id = id;}

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String toString() {
        return "Id: " + getId() + "\nNome: " + this.nome;
    }
}
