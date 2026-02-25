package com.sistema.estudiantes.model;

import java.time.LocalDate;

public class Usuario extends ModelBase {
    private int id;
    private String email;
    private String senha;
    private boolean isAdm;
    private String foto;
    private LocalDate dataNascimento;

    public Usuario() {}

    public Usuario(String email, String senha, boolean isAdm, String foto, LocalDate dataNascimento) {
        this.email = email;
        this.senha = senha;
        this.isAdm = isAdm;
        this.foto = foto;
        this.dataNascimento = dataNascimento;
    }

    public Usuario(int id, String email, String senha, boolean isAdm, String foto, LocalDate dataNascimento) {
        super(id);
        this.email = email;
        this.senha = senha;
        this.isAdm = isAdm;
        this.foto = foto;
        this.dataNascimento = dataNascimento;
    }

    public Usuario(int id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getSenha() {
        return this.senha;
    }

    public boolean getIsAdm() {
        return this.isAdm;
    }

    public String getFoto() {
        return this.foto;
    }

    public LocalDate getDataNascimento() {
        return this.dataNascimento;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setIsAdm(boolean isAdm) {
        this.isAdm = isAdm;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String toString() {
        return "Id: " + getId() + "\nEmail: " + this.email + "\nSenha: " + this.senha + "\nÉ admin? " + this.isAdm + "\nFoto: " + this.foto + "\nData de Nascimento: " + this.dataNascimento;
    }
}