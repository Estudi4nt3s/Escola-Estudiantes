package com.sistema.estudiantes.model;

import java.util.Date;

public class Aluno {
    private int matricula;
    private String nome;
    private Date dataNascimento;
    private String photo;
    private String cpf;
    private int usuario_id;

    public Aluno() {}

    public Aluno(int matricula, String nome, Date dataNascimento,String photo, String cpf ,int usuario_id) {
        this.matricula = matricula;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.photo = photo;
        this.cpf = cpf;
        this.usuario_id = usuario_id;
    }

    public Aluno(int matricula, String nome) {
        this.matricula = matricula;
        this.nome = nome;
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

    public String getPhoto() {
        return this.photo;
    }

    public String getCPF() {
        return this.cpf;
    }


    public int getUsuario_id() {
        return this.usuario_id;
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


    public String toString() {
        return "Matrícula: " + this.matricula + "\nNome: " + this.nome + "\nData Nascimento: " + this.dataNascimento + "\nPhoto: " + this.photo + "\nUsuario_id: " + this.usuario_id;
    }
}
