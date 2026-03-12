package com.sistema.estudiantes.model;

import java.io.Serializable;

public class Admin implements Serializable {

    private int id;
    private static String usuario;
    private String senha;

    public Admin() {
    }

    // Construtor completo
    public Admin(int id, String usuario, String senha) {
        this.id = id;
        this.usuario = usuario;
        this.senha = senha;
    }

    // Getters
    public int getId() {
        return id;
    }

    public static String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", usuario='" + usuario + '\'' +
                '}';
    }
}