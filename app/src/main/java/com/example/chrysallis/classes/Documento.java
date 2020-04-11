package com.example.chrysallis.classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Documento implements Serializable {
    private String nombre;
    private String documento;
    private int id;
    private int id_evento;

    public String getNombre() {
        return nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public int getId() {
        return id;
    }

    public int getId_evento() {
        return id_evento;
    }
}
