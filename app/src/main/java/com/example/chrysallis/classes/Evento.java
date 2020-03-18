package com.example.chrysallis.classes;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.HashSet;

public class Evento implements Serializable {
    private String nombre;
    private String descripcion;
    private int imagen;
    private int id;
    private String fecha;
    private String ubicacion;
    private String hora;
    private String fechaLimite;
    private int numAsistentes;
    private int id_comunidad;
    private HashSet<Asistir> asistir;
    private HashSet<Documento> documentos;
    private HashSet<Notificacion> notificaciones;


    public Evento(String nombre, String descripcion, int imagen, int id, String fecha, String ubicacion, String hora, String fechaLimite, int numAsistentes, int id_comunidad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.id = id;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.hora = hora;
        this.fechaLimite = fechaLimite;
        this.numAsistentes = numAsistentes;
        this.id_comunidad = id_comunidad;
    }

    public Evento(String nombre, String descripcion, int imagen, String fecha, String ubicacion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(String fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public int getNumAsistentes() {
        return numAsistentes;
    }

    public void setNumAsistentes(int numAsistentes) {
        this.numAsistentes = numAsistentes;
    }

    public int getId_comunidad() {
        return id_comunidad;
    }

    public void setId_comunidad(int id_comunidad) {
        this.id_comunidad = id_comunidad;
    }

    public HashSet<Asistir> getAsistir() {
        return asistir;
    }

    public void setAsistir(HashSet<Asistir> asistir) {
        this.asistir = asistir;
    }

    public HashSet<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(HashSet<Documento> documentos) {
        this.documentos = documentos;
    }

    public HashSet<Notificacion> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(HashSet<Notificacion> notificaciones) {
        this.notificaciones = notificaciones;
    }
}
