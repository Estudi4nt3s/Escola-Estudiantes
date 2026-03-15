package com.sistema.estudiantes.model; // ou o pacote que você preferir

public class RankingDTO {
    private String nome;
    private double media;

    // Construtor vazio
    public RankingDTO() {}

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public double getMedia() { return media; }
    public void setMedia(double media) { this.media = media; }
}