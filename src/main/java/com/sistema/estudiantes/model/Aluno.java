package com.sistema.estudiantes.model;

import java.time.LocalDate;

public class Aluno {

    private int matricula;
    private String cpf;
    private String nome;
    private LocalDate dataNascimento;
    private Usuario usuario_id;
    private String telefonePai;
    private int turmaId;

    public Aluno() {}

    public Aluno(int matricula, String cpf, LocalDate dataNascimento, Usuario usuarioId, String telefonePai, int turmaId,String nome) {
        this.nome = nome;
        this.matricula = matricula;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
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

    public Aluno(int matricula, String cpf, LocalDate datanascimento, Usuario u, String telefonepai, int turmaid) {
        this.matricula = matricula;
        this.cpf = cpf;
        this.dataNascimento = datanascimento;
        this.telefonePai = telefonepai;
        this.turmaId = turmaid;
    }

    public int getMatricula() {
        return this.matricula;
    }

    public Usuario getUsuario_id() {
        return usuario_id;}

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

    public void setMatricula(int matricula) {
        this.matricula = matricula;
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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setUsuario_id(Usuario usuario_id) {
        this.usuario_id = usuario_id;
    }

    public void setTurmaId(int turmaId) {
        this.turmaId = turmaId;
    }

    public String toString() {
        return "Matrícula: "+ this.matricula + "\nCpf: " + this.cpf + "\nData de Nascimento: " + this.dataNascimento + "\nId Usuário: " + this.usuario_id + "\nTelefone Pai: " + this.telefonePai + "\nId Turma: " + this.turmaId;
    }
}
