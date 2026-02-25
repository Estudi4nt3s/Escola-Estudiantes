package com.sistema.estudiantes.model;

import java.sql.Time;

public class Aula extends ModelBase {
    private Time horario;
    private Disciplina disciplinaId;
    private Turma turmaId;
    private String diaSemana;

    public Aula() {}

    public Aula(Time horario, Disciplina disciplinaId, Turma turmaId, String diaSemana) {
        this.horario = horario;
        this.disciplinaId = disciplinaId;
        this.turmaId = turmaId;
        this.diaSemana = diaSemana;
    }

    public Aula(int id, Time horario, Disciplina disciplinaId, Turma turmaId, String diaSemana) {
        super(id);
        this.horario = horario;
        this.disciplinaId = disciplinaId;
        this.turmaId = turmaId;
        this.diaSemana = diaSemana;
    }

    public Time getHorario() {
        return this.horario;
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

    public void setHorario(Time horario) {
        this.horario = horario;
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

    public String toString() {
        return "Id: " + getId() + "\nHorario: " + this.horario + "\nId Disciplina: " + this.disciplinaId + "\nId Turma: " + this.turmaId + "\nDia da Semana: " + this.diaSemana;
    }
}
