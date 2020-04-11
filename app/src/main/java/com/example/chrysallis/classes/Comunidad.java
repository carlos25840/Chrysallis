package com.example.chrysallis.classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashSet;

public class Comunidad implements Serializable {
    private int id;
    private String nombre;
    private HashSet<Evento> eventos;
    private HashSet<Socio> socios1;

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public HashSet<Evento> getEventos() {
        return eventos;
    }

    public HashSet<Socio> getSocios1() {
        return socios1;
    }
}
