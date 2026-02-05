package com.sistema.estudiantes.model;

import java.util.Date;

public class Observacao extends ModelBase {
    private String texto;
    private Date dataCriacao;
    private Professor idProfessor;
    private Aluno idAluno;
    private Disciplina idDisciplina;

    public Observacao() {}

    public Observacao(String texto, Date dataCriacao, Professor idProfessor, Aluno idAluno, Disciplina idDisciplina) {
        this.texto = texto;
        this.dataCriacao = dataCriacao;
        this.idProfessor = idProfessor;
        this.idAluno = idAluno;
        this.idDisciplina = idDisciplina;
    }

    public Observacao(int id, String texto, Date dataCriacao, Professor idProfessor, Aluno idAluno, Disciplina idDisciplina) {
        super(id);
        this.texto = texto;
        this.dataCriacao = dataCriacao;
        this.idProfessor = idProfessor;
        this.idAluno = idAluno;
        this.idDisciplina = idDisciplina;
    }

    public String getTexto() {
        return this.texto;
    }

    public Date getDataCriacao() {
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

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setDataCriacao(Date dataCriacao) {
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
        return "Id: " + getId() + "\nTexto: " + this.texto + "\nData Criação: " + this.dataCriacao + "\nId Professor: " + this.idProfessor + "\nId Aluno: " + this.idAluno + "\nId Disciplina: " + this.idDisciplina;
    }

}