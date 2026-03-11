package com.sistema.estudiantes.model;

public class Professor {
    private int id;
    private String nome;
    private Usuario usuario;
    private Disciplina disciplina;

    public Professor() {}

    public Professor(int id) {
        this.id = id;
    }

    // Construtor completo
    public Professor(int id, String nome, Usuario usuario, Disciplina disciplina) {
        this.id = id;
        this.nome = nome;
        this.usuario = usuario;
        this.disciplina = disciplina;
    }

    public Professor(int id, Usuario usuario, Disciplina disciplina) {
        this.id = id;
        this.usuario = usuario;
        this.disciplina = disciplina;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Disciplina getDisciplina() { return disciplina; }
    public void setDisciplina(Disciplina disciplina) { this.disciplina = disciplina; }
}