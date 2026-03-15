package com.sistema.estudiantes.model;

import java.time.LocalDate;

public class Aluno {

    private int matricula;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private Usuario usuario_id;
    private String telefonePai;
    private int turmaId;
    private String emailResponsavel;

    public Aluno() {}

    public Aluno(int matricula, String nome, String cpf, LocalDate dataNascimento, Usuario usuarioId, String telefonePai, int turmaId) {
        this.matricula = matricula;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.usuario_id = usuarioId;
        this.telefonePai = telefonePai;
        this.turmaId = turmaId;
    }

    public Aluno(int matricula, int turmaId) {
        this.matricula = matricula;
        this.turmaId = turmaId;
    }

    public Aluno(int matricula){
        this.matricula = matricula;
    }

    public int getMatricula() {
        return this.matricula;
    }

    public String getNome() {return nome;}

    public String getCpf() {
        return this.cpf;
    }

    public LocalDate getDataNascimento() {
        return this.dataNascimento;
    }

    public Usuario getUsuarioId() {
        return this.usuario_id;
    }

    public String getTelefonePai() {
        return this.telefonePai;
    }

    public int getTurmaId() {
        return this.turmaId;
    }

    public String getEmailResponsavel() {
        return emailResponsavel;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setUsuarioId(Usuario usuarioId) {
        this.usuario_id = usuarioId;
    }

    public void setTelefonePai(String telefonePai) {
        this.telefonePai = telefonePai;
    }

    public void setTurmaId(int turmaId) {
        this.turmaId = turmaId;
    }

    public void setEmailResponsavel(String emailResponsavel) {
        this.emailResponsavel = emailResponsavel;
    }

    public String toString() {
        return "Matrícula: "+ this.matricula + "\nNome: " + this.nome + "\nCpf: " + this.cpf + "\nData de Nascimento: " + this.dataNascimento + "\nId Usuário: " + this.usuario_id + "\nTelefone Pai: " + this.telefonePai + "\nId Turma: " + this.turmaId;
    }
}
