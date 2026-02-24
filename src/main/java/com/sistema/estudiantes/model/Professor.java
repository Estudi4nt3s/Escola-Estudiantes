package com.sistema.estudiantes.model;

public class Professor extends ModelBase {

    private int id;
    private String nome;


    public Professor() {}

    public Professor(String nome) {
        this.nome = nome;
    }

    public Professor(int id, String nome) {
        super(id);
        this.nome = nome;
    }

    public Professor(int id){
        this.id = id;
    }

    public int getId(){return this.id;}

    public String getNome() {
        return this.nome;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }


    public String toString() {
        return "Id: " + getId() + "\nNome: " + this.nome;
    }
}
