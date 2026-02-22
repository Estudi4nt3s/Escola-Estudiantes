package com.sistema.estudiantes.model;

import java.util.Date;

public class Aluno {
    private int matricula;
    private String nome;
    private Date dataNascimento;
    private String senha;

    public Aluno() {}

    public Aluno(int matricula, String nome, Date dataNascimento, String senha) {
        this.matricula = matricula;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.senha = senha;
    }

    public Aluno(int matricula){
        this.matricula = matricula;
    }

    public int getMatricula() {
        return this.matricula;
    }

    public String getNome() {
        return this.nome;
    }

    public Date getDataNascimento() {
        return this.dataNascimento;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String toString() {
        return "Matrícula: " + this.matricula + "\nNome: " + this.nome + "\nData Nascimento: " + this.dataNascimento + "\nSenha: " + this.senha;
    }
}
