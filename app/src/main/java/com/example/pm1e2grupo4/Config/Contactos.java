package com.example.pm1e2grupo4.Config;

public class Contactos {

    private int id;
    private String nombre;

    private String numero;
    private String latitud;
    private String longitud;


    public Contactos(){

    }

    public Contactos(int id, String nombre, String numero, String latitud, String longitud) {
        this.id = id;
        this.nombre = nombre;
        this.numero = numero;
        this.latitud = latitud;
        this.longitud = longitud;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }


}
