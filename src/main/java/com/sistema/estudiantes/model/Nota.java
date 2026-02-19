package com.sistema.estudiantes.model;

public class Nota extends ModelBase {
    private Aluno aluno;
    private Disciplina disciplina;
    private Turma turma;
    private Disciplina idDisciplina;
    private Aluno idAluno;
    private Turma idTurma;
    private double valor;

    public Nota() {}

    public Nota(Disciplina idDisciplina, Aluno idAluno, Turma idTurma, double valor) {
        this.idDisciplina = idDisciplina;
        this.idAluno = idAluno;
        this.idTurma = idTurma;
        this.valor = valor;
    }

    public Nota(int id, Disciplina idDisciplina, Aluno idAluno, Turma idTurma, double valor) {
        super(id);
        this.idDisciplina = idDisciplina;
        this.idAluno = idAluno;
        this.idTurma = idTurma;
        this.valor = valor;
    }

    public Aluno getAluno(){return this.aluno;}

    public Turma getTurma(){return this.turma;}

    public Disciplina getIdDisciplina() {
        return this.idDisciplina;
    }

    public Disciplina getDisciplina() {return this.disciplina;}

    public Aluno getIdAluno() {
        return this.idAluno;
    }

    public Turma getIdTurma() {
        return this.idTurma;
    }

    public double getValor() {
        return this.valor;
    }

    public void setIdDisciplina(Disciplina idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public void setIdAluno(Aluno idAluno) {
        this.idAluno = idAluno;
    }

    public void setIdTurma(Turma idTurma) {
        this.idTurma = idTurma;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String toString() {
        return "Id: " + getId() + "\nId Disciplina: " + this.idDisciplina + "\nId Aluno: " + this.idAluno + "\nValor: " + this.valor;
    }

}
