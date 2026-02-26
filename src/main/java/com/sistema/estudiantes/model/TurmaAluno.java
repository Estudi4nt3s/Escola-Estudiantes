package com.sistema.estudiantes.model;

public class TurmaAluno {
    private int id;
    private Aluno matriculaAluno;
    private Turma idTurma;

    public TurmaAluno() {}

    public TurmaAluno(int id, Aluno matriculaAluno, Turma idTurma) {
        this.id = id;
        this.matriculaAluno = matriculaAluno;
        this.idTurma = idTurma;
    }

    public int getId() {
        return this.id;
    }

    public Aluno getMatriculaAluno() {
        return this.matriculaAluno;
    }

    public Turma getIdTurma() {
        return this.idTurma;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMatriculaAluno(Aluno matriculaAluno) {
        this.matriculaAluno = matriculaAluno;
    }

    public void setIdTurma(Turma idTurma) {
        this.idTurma = idTurma;
    }

    public String toString() {
        return "Id: " + this.id + "\nMatricula Aluno: " + this.matriculaAluno + "\nId Turma: " + this.idTurma;
    }
}