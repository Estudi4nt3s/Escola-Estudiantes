package com.sistema.estudiantes.model;

import java.time.LocalDate;

public class Observacao {
    private int id;
    private String texto;
    private LocalDate dataCriacao;
    private Professor idProfessor;
    private Aluno idAluno;
    private Disciplina idDisciplina;

    public Observacao() {}

    public Observacao(String texto, LocalDate dataCriacao, Professor idProfessor, Aluno idAluno, Disciplina idDisciplina) {
        this.texto = texto;
        this.dataCriacao = dataCriacao;
        this.idProfessor = idProfessor;
        this.idAluno = idAluno;
        this.idDisciplina = idDisciplina;
    }

    public Observacao(int id, String texto, LocalDate dataCriacao, Professor idProfessor, Aluno idAluno, Disciplina idDisciplina) {
        this.id = id;
        this.texto = texto;
        this.dataCriacao = dataCriacao;
        this.idProfessor = idProfessor;
        this.idAluno = idAluno;
        this.idDisciplina = idDisciplina;
    }

    public int getId() {
        return this.id;
    }

    public String getTexto() {
        return this.texto;
    }

    public LocalDate getDataCriacao() {
        return this.dataCriacao;
    }

    public Professor getIdProfessor() {
        return this.idProfessor;
    }

    public Aluno getIdAluno() {
        return this.idAluno;
    }

    public Disciplina getIdDisciplina() {
        return this.idDisciplina;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public void setIdProfessor(Professor idProfessor) {
        this.idProfessor = idProfessor;
    }

    public void setIdAluno(Aluno idAluno) {
        this.idAluno = idAluno;
    }

    public void setIdDisciplina(Disciplina idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public String toString() {
        return "Id: " + this.id + "\nTexto: " + this.texto + "\nData Criação: " + this.dataCriacao + "\nId Professor: " + this.idProfessor + "\nId Aluno: " + this.idAluno + "\nId Disciplina: " + this.idDisciplina;
    }

}