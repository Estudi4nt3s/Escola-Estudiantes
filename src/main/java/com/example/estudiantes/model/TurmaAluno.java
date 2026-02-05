package com.example.estudiantes.model;

public class TurmaAluno extends ModelBase {
    private Aluno matriculaAluno;
    private Turma idTurma;

    public TurmaAluno() {}

    public TurmaAluno(Aluno matriculaAluno, Turma idTurma) {
        this.matriculaAluno = matriculaAluno;
        this.idTurma = idTurma;
    }

    public TurmaAluno(int id, Aluno matriculaAluno, Turma idTurma) {
        super(id);
        this.matriculaAluno = matriculaAluno;
        this.idTurma = idTurma;
    }

    public Aluno getMatriculaAluno() {
        return this.matriculaAluno;
    }

    public Turma getIdTurma() {
        return this.idTurma;
    }

    public void setMatriculaAluno(Aluno matriculaAluno) {
        this.matriculaAluno = matriculaAluno;
    }

    public void setIdTurma(Turma idTurma) {
        this.idTurma = idTurma;
    }
}