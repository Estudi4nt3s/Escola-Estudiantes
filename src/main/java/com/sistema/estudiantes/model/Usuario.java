package com.sistema.estudiantes.model;

public class Usuario {
    private Integer id;
    private String email;
    private String senha;
    private String foto;

    public Usuario() {}

    public Usuario(Integer id, String email, String senha, String foto) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.foto = foto;
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

    public String toString() {
        return "Id: " + this.id + "\nEmail: " + this.email + "\nSenha: " + this.senha + "\nFoto: " + this.foto;
    }
}