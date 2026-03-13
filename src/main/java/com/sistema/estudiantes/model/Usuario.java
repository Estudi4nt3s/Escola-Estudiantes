package com.sistema.estudiantes.model;

import java.time.LocalDateTime;

public class Usuario {
    private Integer id;
    private String email;
    private String senha;
    private String foto;
    private String tokenRecuperacao;
    private LocalDateTime tokenExpira;

    public Usuario() {}

    public Usuario(Integer id, String email, String senha, String foto) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.foto = foto;
    }

    public Usuario(Integer id, String tokenRecuperacao, LocalDateTime tokenExpira) {
        this.id = id;
        this.tokenRecuperacao = tokenRecuperacao;
        this.tokenExpira = tokenExpira;
    }

    public Usuario( String email, String senha, String foto) {
        this.email = email;
        this.senha = senha;
        this.foto = foto;
    }

    public Usuario(Integer id, String email, String senha) {
        this.id = id;
        this.email = email;
        this.senha = senha;
    }

    public Usuario(Integer id) {
        this.id = id;
    }

    public Usuario(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public Integer getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getSenha() {
        return this.senha;
    }

    public String getFoto() {
        return this.foto;
    }

    public String getTokenRecuperacao() {
        return this.tokenRecuperacao;
    }

    public LocalDateTime getTokenExpira() {
        return this.tokenExpira;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setTokenRecuperacao(String tokenRecuperacao) {
        this.tokenRecuperacao = tokenRecuperacao;
    }

    public void setTokenExpira(LocalDateTime tokenExpira) {
        this.tokenExpira = tokenExpira;
    }

    public String toString() {
        return "Id: " + this.id + "\nEmail: " + this.email + "\nSenha: " + this.senha + "\nFoto: " + this.foto + "\nToken Recuperacao: " + this.tokenRecuperacao + "\nToken Expira: " + this.tokenExpira;
    }
}