package com.example.pm1e2grupo4.Config;

public class Transacciones {

    // Nombre de la base de datos
    public static final String NameDatabase = "app_registrocontacto";
    // Tablas de la base de datos
    public static final String tablacontactos = "contactos";

    /* Transacciones de la base de datos app_registrocontacto */
    public static final String CreateTBContactos =
            "CREATE TABLE contactos (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, pais TEXT, numero TEXT, nota TEXT)";

    public static final String DropTableContactos = "DROP TABLE IF EXISTS contactos";

    // Helpers
    public static final String Empty = "";

}
