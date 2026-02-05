package com.example.estudiantes.model;

public abstract class ModelBase {
    private int id;

    public ModelBase(){}

    public ModelBase(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return "Id: " + this.id;
    }
}
