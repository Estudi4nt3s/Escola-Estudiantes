package com.sistema.estudiantes.model;

public class TurmaAluno extends ModelBase {
    private Aluno aluno;
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

    public TurmaAluno(int id, int matriculaAluno, int idTurma) {
        super(id);
        this.matriculaAluno = new Aluno(matriculaAluno);
        this.idTurma = new Turma(idTurma);
    }

    public Aluno getAluno(){return this.aluno;}

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