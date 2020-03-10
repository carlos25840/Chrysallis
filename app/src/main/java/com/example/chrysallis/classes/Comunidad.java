package com.example.chrysallis.classes;

import java.io.Serializable;
import java.util.HashSet;

public class Comunidad implements Serializable {

    private int id;
    private String nombre;
    private HashSet<Evento> eventos;
    private HashSet<Socio> socios1;

}
