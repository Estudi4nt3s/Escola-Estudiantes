package com.sistema.estudiantes.model;

public class Nota {
    private int id;
    private Disciplina idDisciplina;
    private Aluno idAluno;
    private Turma idTurma;
    private double valor;

    public Nota() {}

    public Nota(int id, Disciplina idDisciplina, Aluno idAluno, Turma idTurma, double valor) {
        this.id = id;
        this.idDisciplina = idDisciplina;
        this.idAluno = idAluno;
        this.idTurma = idTurma;
        this.valor = valor;
    }

    public int getId() {
        return this.id;
    }

    public Disciplina getIdDisciplina() {
        return this.idDisciplina;
    }

    public Aluno getIdAluno() {
        return this.idAluno;
    }

    public Turma getIdTurma() {
        return this.idTurma;
    }

    public double getValor() {
        return this.valor;
    }

    public void setId(int id) {
        this.id = id;
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
        return "Id: " + this.id + "\nId Disciplina: " + this.idDisciplina + "\nId Aluno: " + this.idAluno + "\nId Turma: " + this.idTurma + "\nValor: " + this.valor;
    }

}
