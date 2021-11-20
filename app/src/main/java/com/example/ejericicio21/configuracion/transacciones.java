package com.example.ejericicio21.configuracion;

public class transacciones {
    public static final String NameDatabase = "PMO1DB";

    //tablas de la DB en SQLite

    public static final String tableVideo = "tableVideo";

    //campo de la tabla personas de la DB en SQLite
    public static final String id = "id";
    public static final String video = "video";


    //transacciones DDL(DATA DEFINITION LENGUAGE) tabla personas
    public static final String CreateTableVideo = "CREATE TABLE tableVideo (id INTEGER PRIMARY KEY AUTOINCREMENT "+
            ",video BLOB)";
    public static final String DROPTableVideo = "DROP TABLE IF EXISTS tableVideo";
}
