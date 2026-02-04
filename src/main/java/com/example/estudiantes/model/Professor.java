package com.example.estudiantes.model;

public class Professor extends ModelBase {
    private String nome;
    private String senha;

    public Professor() {}

    public Professor(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
    }

    public Professor(int id, String nome, String senha) {
        super(id);
        this.nome = nome;
        this.senha = senha;
    }

    public String getNome() {
        return this.nome;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String toString() {
        return "Id: " + getId() + "\nNome: " + this.nome + "\nSenha: " + this.senha;
    }
}
