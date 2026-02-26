package com.sistema.estudiantes.model;

public class Usuario {
    private int id;
    private String email;
    private String senha;
    private boolean isAdm;
    private String foto;

    public Usuario() {}

    public Usuario(int id, String email, String senha, boolean isAdm, String foto) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.isAdm = isAdm;
        this.foto = foto;
    }

    public Usuario( String email, String senha, boolean isAdm, String foto) {
        this.email = email;
        this.senha = senha;
        this.isAdm = isAdm;
        this.foto = foto;
    }

    public Usuario(int id) {
        this.id = id;
    }

    public Usuario(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public int getId() {
        return this.id;
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

    public void setId(int id) {
        this.id = id;
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

    public String toString() {
        return "Id: " + this.id + "\nEmail: " + this.email + "\nSenha: " + this.senha + "\nÉ admin? " + this.isAdm + "\nFoto: " + this.foto;
    }
}