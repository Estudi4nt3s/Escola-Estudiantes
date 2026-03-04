package com.sistema.estudiantes.model;

public class Nota {
    private int id;
    private Disciplina idDisciplina;
    private Aluno idAluno;
    private Turma idTurma;
    private double N1;
    private double N2;

    public Nota() {}

    public Nota(int id, Disciplina idDisciplina, Aluno idAluno, Turma idTurma, double N1, double N2) {
        this.id = id;
        this.idDisciplina = idDisciplina;
        this.idAluno = idAluno;
        this.idTurma = idTurma;
        this.N1 = N1;
        this.N2 = N2;
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

    public double getN1() {
        return this.N1;
    }

    public double getN2() {
        return this.N2;
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

    public void setN1(double N1) {
        this.N1 = N1;
    }

    public  void setN2(double N2) {
        this.N2 = N2;
    }

    public String toString() {
        return "Id: " + this.id + "\nId Disciplina: " + this.idDisciplina + "\nId Aluno: " + this.idAluno + "\nId Turma: " + this.idTurma + "\nN1: " + this.N1 + "\nN2" + this.N2;
    }

}
