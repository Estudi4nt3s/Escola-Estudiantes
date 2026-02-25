package com.sistema.estudiantes.model;

import java.time.LocalTime;

public class Aula {

    private int id;
    private LocalTime horario;
    private int disciplinaId;
    private int turmaId;
    private String diaSemana;

    public Aula() {}

    public Aula(int id, LocalTime horario, int disciplinaId, int turmaId, String diaSemana) {
        this.id = id;
        this.horario = horario;
        this.disciplinaId = disciplinaId;
        this.turmaId = turmaId;
        this.diaSemana = diaSemana;
    }

    public Aula(int id) {
        this.id = id;
    }

    public Aula(int id, String diaSemana) {
        this.id = id;
        this.diaSemana = diaSemana;
    }

    public int getId() {
        return this.id;
    }

    public LocalTime getHorario() {
        return this.horario;
    }

    public int getDisciplinaId() {
        return this.disciplinaId;
    }

    public int getTurmaId() {
        return this.turmaId;
    }

    public String getDiaSemana() {
        return this.diaSemana;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public void setDisciplinaId(int disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    public void setTurmaId(int turmaId) {
        this.turmaId = turmaId;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    @Override
    public String toString() {
        return "Id: " + this.id +
                "\nHorário: " + this.horario +
                "\nDisciplinaId: " + this.disciplinaId +
                "\nTurmaId: " + this.turmaId +
                "\nDia da Semana: " + this.diaSemana;
    }
}