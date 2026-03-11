package com.sistema.estudiantes.model;

public class Professor {
    private int id;
    private Usuario usuario;
    private Disciplina disciplina;

    public Professor() {}

    public Professor(int id) {
        this.id = id;
    }

    // Construtor completo
    public Professor(int id, Usuario usuario, Disciplina disciplina) {
        this.id = id;
        this.usuario = usuario;
        this.disciplina = disciplina;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Disciplina getDisciplina() { return disciplina; }
    public void setDisciplina(Disciplina disciplina) { this.disciplina = disciplina; }
}