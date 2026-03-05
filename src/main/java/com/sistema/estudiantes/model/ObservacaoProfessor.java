package com.sistema.estudiantes.model;

import java.time.LocalDate;

public class ObservacaoProfessor {
    private int id;
    private String texto;
    private LocalDate dataCriacao;
    private Professor idProfessor;
    private Aluno idAluno;
    private Disciplina idDisciplina;
    private String nomeProfessor;
    private String nomeDisciplina;

    public ObservacaoProfessor() {}

    public ObservacaoProfessor(String texto, LocalDate dataCriacao, Professor idProfessor, Aluno idAluno, Disciplina idDisciplina, String nomeProfessor, String nomeDisciplina) {
        this.texto = texto;
        this.dataCriacao = dataCriacao;
        this.idProfessor = idProfessor;
        this.idAluno = idAluno;
        this.idDisciplina = idDisciplina;
        this.nomeProfessor = nomeProfessor;
        this.nomeDisciplina = nomeDisciplina;
    }

    public ObservacaoProfessor(int id, String texto, LocalDate dataCriacao, Professor idProfessor, Aluno idAluno, Disciplina idDisciplina, String nomeProfessor, String nomeDisciplina) {
        this.id = id;
        this.texto = texto;
        this.dataCriacao = dataCriacao;
        this.idProfessor = idProfessor;
        this.idAluno = idAluno;
        this.idDisciplina = idDisciplina;
        this.nomeProfessor = nomeProfessor;
        this.nomeDisciplina = nomeDisciplina;
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

    public  String getNomeProfessor() {
        return this.nomeProfessor;
    }

    public String getNomeDisciplina() {
        return this.nomeDisciplina;
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

    public void setNomeProfessor(String nomeProfessor) {
        this.nomeProfessor = nomeProfessor;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }
}
