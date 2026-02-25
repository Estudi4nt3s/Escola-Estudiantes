package com.sistema.estudiantes.model;

import java.util.Date;

public class Aluno {
    private int matricula;
    private String cpf;
    private String nome;
    private Usuario usuarioId;
    private String telefonePai;

    public Aluno() {}

    public Aluno(int matricula, String cpf, String nome, Usuario usuarioId, String telefonePai) {
        this.matricula = matricula;
        this.cpf = cpf;
        this.nome = nome;
        this.usuarioId = usuarioId;
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

    public Usuario getUsuarioId() {
        return this.usuarioId;
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

    public void setUsuarioId(Usuario usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setTelefonePai(String telefonePai) {
        this.telefonePai = telefonePai;
    }

    public String toString() {
        return "Matrícula: "+ this.matricula + "\nCpf: " + this.cpf  + "\nNome: " + this.nome + "\nId Usuário: " + this.usuarioId + "\nTelefone Pai: " + this.telefonePai;
    }
}
