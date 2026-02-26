package com.sistema.estudiantes.model;

import java.time.LocalDate;

public class Aluno {
    private int matricula;
    private String cpf;
    private String nome;
    private LocalDate dataNascimento;
    private Usuario usuario_id;
    private String telefonePai;

    public Aluno() {}

    public Aluno(int matricula, String cpf, String nome, LocalDate dataNascimento, Usuario usuarioId, String telefonePai) {
        this.matricula = matricula;
        this.cpf = cpf;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.usuario_id = usuarioId;
        this.telefonePai = telefonePai;
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

    public String getCpf() {
        return this.cpf;
    }

    public String getNome() {
        return this.nome;
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


    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String toString() {
        return "Matrícula: "+ this.matricula + "\nCpf: " + this.cpf  + "\nNome: " + this.nome + "\nData de Nascimento: " + this.dataNascimento + "\nId Usuário: " + this.usuario_id + "\nTelefone Pai: " + this.telefonePai;
    }
}
