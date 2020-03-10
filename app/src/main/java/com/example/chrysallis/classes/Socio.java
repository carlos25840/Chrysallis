package com.example.chrysallis.classes;

import java.io.Serializable;
import java.util.HashSet;

public class Socio implements Serializable {
    private int id;
    private String dni;
    private String telefono;
    private String nombre;
    private int imagen;
    private String apellidos;
    private boolean activo;
    private String mail;
    private String password;
    private boolean administrador;
    private String idiomaDefecto;
    private boolean estatal;
    private int id_comunidad;
    private HashSet<Comunidad> comunidades1;

    public int getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public boolean isActivo() {
        return activo;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public String getIdiomaDefecto() {
        return idiomaDefecto;
    }

    public boolean isEstatal() {
        return estatal;
    }

    public int getId_comunidad() {
        return id_comunidad;
    }

    public HashSet<Comunidad> getComunidades1() {
        return comunidades1;
    }

    public int getImagen() {
        return imagen;
    }
}
