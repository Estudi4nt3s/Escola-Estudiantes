package com.sistema.estudiantes.model;

import java.time.LocalTime;

public class Aula {

    private int id;
    private LocalTime horarioInicio;
    private LocalTime horarioFim;
    private Disciplina disciplinaId;
    private Turma turmaId;
    private String diaSemana;

    public Aula() {}

    public Aula(int id, LocalTime horarioInicio, LocalTime horarioFim, Disciplina disciplinaId, Turma turmaId, String diaSemana) {
        this.id = id;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
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

    public LocalTime getHorarioInicio() {
        return this.horarioInicio;
    }

    public LocalTime getHorarioFim() {
        return this.horarioFim;
    }

    public Disciplina getDisciplinaId() {
        return this.disciplinaId;
    }

    public Turma getTurmaId() {
        return this.turmaId;
    }

    public String getDiaSemana() {
        return this.diaSemana;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHorarioInicio(LocalTime horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public void setHorarioFim(LocalTime horarioFim) {
        this.horarioFim = horarioFim;
    }

    public void setDisciplinaId(Disciplina disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    public void setTurmaId(Turma turmaId) {
        this.turmaId = turmaId;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    @Override
    public String toString() {
        return "Id: " + this.id +
                "\nHorário Início: " + this.horarioInicio +
                "\nHorário Fim: " + this.horarioFim +
                "\nId Disciplina: " + this.disciplinaId +
                "\nId Turma: " + this.turmaId +
                "\nDia da Semana: " + this.diaSemana;
    }
}