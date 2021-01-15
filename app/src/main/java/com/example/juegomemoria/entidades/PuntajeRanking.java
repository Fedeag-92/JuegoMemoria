package com.example.juegomemoria.entidades;

public class PuntajeRanking {
    private String username;
    private Integer puntaje;

    public PuntajeRanking(String username, Integer puntaje) {
        this.username = username;
        this.puntaje = puntaje;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Integer puntaje) {
        this.puntaje = puntaje;
    }
}