package com.sistema.estudiantes.model;

public class Admin{

    private int id;
    private static String usuario;
    private String senha;

    public int getId() {
        return id;
    }

    public static String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
}
