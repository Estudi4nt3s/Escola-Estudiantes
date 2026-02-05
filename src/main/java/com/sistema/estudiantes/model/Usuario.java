package com.sistema.estudiantes.model;

public class Usuario extends ModelBase {
    private String email;
    private String senha;

    public Usuario() {}

    public Usuario(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public Usuario(int id, String email, String senha) {
        super(id);
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() {
        return this.email;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String toString() {
        return "Id: " + getId() + "\nEmail: " + this.email + "\nSenha: " + this.senha;
    }
}
