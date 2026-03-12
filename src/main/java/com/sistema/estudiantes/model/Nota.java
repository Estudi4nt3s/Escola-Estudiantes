package com.sistema.estudiantes.model;

public class Nota {
    private int id;
    private Disciplina idDisciplina;
    private Aluno idAluno;
    private Double N1;
    private Double N2;

    public Nota() {}

    public Nota(int id, Disciplina idDisciplina, Aluno idAluno, Double N1, Double N2) {
        this.id = id;
        this.idDisciplina = idDisciplina;
        this.idAluno = idAluno;
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

    public Double getN1() {
        return this.N1;
    }

    public Double getN2() {
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

    public void setN1(Double N1) {
        this.N1 = N1;
    }

    public  void setN2(Double N2) {
        this.N2 = N2;
    }

    public String toString() {
        return "Id: " + this.id + "\nId Disciplina: " + this.idDisciplina + "\nId Aluno: " + this.idAluno +  "\nN1: " + this.N1 + "\nN2" + this.N2;
    }

}
