package com.sistema.estudiantes.model;

public class Usuario {
    private int id;
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private String foto;

    public Usuario() {}

    public Usuario(int id, String nome, String sobrenome, String email, String senha, String foto) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.senha = senha;
        this.foto = foto;
    }

    public Usuario( String email, String senha, String foto) {
        this.email = email;
        this.senha = senha;
        this.foto = foto;
    }

    public Usuario(int id, String email, String senha) {
        this.id = id;
        this.email = email;
        this.senha = senha;
    }

    public Usuario(int id) {
        this.id = id;
    }

    public Usuario(String nome, String sobrenome, String email, String senha) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.senha = senha;
    }

    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public String getSobrenome() {
        return this.sobrenome;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
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

    public String toString() {
        return "Id: " + this.id + "\nEmail: " + this.email + "\nSenha: " + this.senha + "\nFoto: " + this.foto;
    }
}