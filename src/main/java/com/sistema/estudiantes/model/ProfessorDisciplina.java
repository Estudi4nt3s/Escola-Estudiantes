package com.sistema.estudiantes.model;

public class ProfessorDisciplina extends ModelBase {
    private Professor idProfessor;
    private Disciplina idDisciplina;

    public ProfessorDisciplina() {}

    public ProfessorDisciplina(Professor idProfessor, Disciplina idDisciplina) {
        this.idProfessor = idProfessor;
        this.idDisciplina = idDisciplina;
    }

    public ProfessorDisciplina(int id, Professor idProfessor, Disciplina idDisciplina) {
        super(id);
        this.idProfessor = idProfessor;
        this.idDisciplina = idDisciplina;
    }

    public Professor getIdProfessor() {
        return this.idProfessor;
    }

    public Disciplina getIdDisciplina() {
        return this.idDisciplina;
    }

    public void setIdProfessor(Professor idProfessor) {
        this.idProfessor = idProfessor;
    }

    public void setIdDisciplina(Disciplina idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public String toString() {
        return "Id: " + getId() + "\nId Professor: " + this.idProfessor + "\nId Disciplina: " + this.idDisciplina;
    }
}
