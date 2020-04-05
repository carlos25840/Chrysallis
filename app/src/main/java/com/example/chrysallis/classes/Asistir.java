package com.example.chrysallis.classes;

import java.io.Serializable;
import java.util.Objects;

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

    public Asistir(int id_socio, int id_evento) {
        this.id_socio = id_socio;
        this.id_evento = id_evento;
    }

    public int getId_socio() {
        return id_socio;
    }

    public int getId_evento() {
        return id_evento;
    }

    public int getValoracion() {
        return valoracion;
    }

    public String getComentario() {
        return comentario;
    }

    public int getCuantos() {
        return cuantos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Asistir asistir = (Asistir) o;
        return id_socio == asistir.id_socio &&
                id_evento == asistir.id_evento;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_socio, id_evento);
    }
}
