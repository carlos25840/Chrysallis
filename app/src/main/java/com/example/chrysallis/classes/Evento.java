package com.example.chrysallis.classes;

import java.sql.Time;
import java.util.Date;
import java.util.HashSet;

public class Evento {
    private String nombre;
    private String descripcion;
    private int imagen;
    private int id;
    private Date fecha;
    private String ubicacion;
    private Time hora;
    private Date fechaLimite;
    private int numAsistentes;
    private int id_comunidad;
    private HashSet<Asistir> asistir;
    private HashSet<Documento> documentos;
    private HashSet<Notificacion> notificaciones;


    public Evento(String nombre, String descripcion, int imagen, int id, Date fecha, String ubicacion, Time hora, Date fechaLimite, int numAsistentes, int id_comunidad) {
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

    public Evento(String nombre, String descripcion, int imagen, Date fecha, String ubicacion) {
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
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
