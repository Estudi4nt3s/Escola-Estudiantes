package com.sistema.estudiantes.model;

public class DisciplinasAdm {

    private int id;
    private String nome;
    private int cargaHoraria;

    private String professorNome;
    private String turmaNome;

    public DisciplinasAdm() {}

    // Construtor completo
    public DisciplinasAdm(int id, String nome, int cargaHoraria,
                      String professorNome, String turmaNome) {
        this.id = id;
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.professorNome = professorNome;
        this.turmaNome = turmaNome;
    }

    // GETTERS
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public String getProfessorNome() {
        return professorNome;
    }

    public String getTurmaNome() {
        return turmaNome;
    }

    // SETTERS
    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public void setProfessorNome(String professorNome) {
        this.professorNome = professorNome;
    }

    public void setTurmaNome(String turmaNome) {
        this.turmaNome = turmaNome;
    }
}