package com.example.chrysallis.classes;

import java.io.Serializable;

public class Asistir implements Serializable {

    private int id_socio;
    private int id_evento;
    private int valoracion;
    private String comentario;
    private int cuantos;

    public Asistir(int id_socio, int id_evento, int cuantos) {
        this.id_socio = id_socio;
        this.id_evento = id_evento;
        this.cuantos = cuantos;
    }
}
