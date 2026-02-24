package com.sistema.estudiantes.model;

public class Professor extends ModelBase {

    private int id;
    private String nome;
    private int usuario_id;

    public Professor() {}

    public Professor(String nome, int usuario_id) {
        this.nome = nome;
        this.usuario_id = usuario_id;
    }

    public Professor(int id, String nome, int usuario_id) {
        super(id);
        this.nome = nome;
        this.usuario_id = usuario_id;
    }

    public Professor(int id){
        this.id = id;
    }

    public int getId(){return this.id;}

    public String getNome() {
        return this.nome;
    }

    public int getUsuario_id() {
        return this.usuario_id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String toString() {
        return "Id: " + getId() + "\nNome: " + this.nome + "\nUsuario_id: " + this.usuario_id;
    }
}
