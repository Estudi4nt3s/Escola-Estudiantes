package com.sistema.estudiantes.model;

public class ProfessorDisciplina {
    private int id;
    private Professor idProfessor;
    private Disciplina idDisciplina;

    public ProfessorDisciplina() {}

    public ProfessorDisciplina(int id, Professor idProfessor, Disciplina idDisciplina) {
        this.id= id;
        this.idProfessor = idProfessor;
        this.idDisciplina = idDisciplina;
    }

    public ProfessorDisciplina(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public Professor getIdProfessor() {
        return this.idProfessor;
    }

    public Disciplina getIdDisciplina() {
        return this.idDisciplina;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdProfessor(Professor idProfessor) {
        this.idProfessor = idProfessor;
    }

    public void setIdDisciplina(Disciplina idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public String toString() {
        return "Id: " + this.id + "\nId Professor: " + this.idProfessor + "\nId Disciplina: " + this.idDisciplina;
    }
}
