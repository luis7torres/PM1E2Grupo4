package com.example.pm1e2grupo4.Config;

public class Personas {

    private String id;
    private String nombre;
    private String telefono;
    private String latitude;
    private String longitud;
    //private String firma;

    public Personas() {
    }

    public Personas(String id, String nombre, String telefono, String latitude, String longitud) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.latitude = latitude;
        this.longitud = longitud;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }


}
