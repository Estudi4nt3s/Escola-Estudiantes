package com.sistema.estudiantes.model;

public class Professor {

    private int id;
    private String nome;
    private Usuario usuarioId;
    private Disciplina disciplinaId;

    public Professor() {}

    public Professor(int id, String nome, Usuario usuarioId, Disciplina disciplinaId) {
        this.id = id;
        this.nome = nome;
        this.usuarioId = usuarioId;
        this.disciplinaId = disciplinaId;
    }

    public Professor(int id){
        this.id = id;
    }

    public int getId(){return this.id;}

    public String getNome() {
        return this.nome;
    }

    public Usuario getUsuarioId() {
        return this.usuarioId;
    }

    public Disciplina getDisciplinaId() {
        return this.disciplinaId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setUsuarioId(Usuario usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setDisciplinaId(Disciplina disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    public String toString() {
        return "Id: " + this.id + "\nNome: " + this.nome + "\nId Usuário: " + this.usuarioId + "\nId Disciplina: " + this.disciplinaId;
    }
}