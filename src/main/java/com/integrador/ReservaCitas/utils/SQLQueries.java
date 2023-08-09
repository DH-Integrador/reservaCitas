package com.integrador.ReservaCitas.utils;

public class SQLQueries {
    public static final String CREATETABLES =
            "DROP TABLE IF EXISTS ODONTOLOGOS;" +
                    "CREATE TABLE ODONTOLOGOS(MATRICULA VARCHAR(255) PRIMARY KEY, NOMBRE VARCHAR(255), APELLIDO VARCHAR(255));" ;
    public static final String INSERT_CUSTOM = "INSERT INTO ODONTOLOGOS VALUES(?,?,?);";
    public static final String UPDATE_MATRICULA = "UPDATE ODONTOLOGOS SET MATRICULA=? WHERE NOMBRE=?";

    public static final String SELECT = "SELECT * FROM ODONTOLOGOS WHERE MATRICULA=?";

    public static final String SELECT_ALL = "SELECT * FROM ODONTOLOGOS";

    public static final String DELETE = "DELETE FROM odontologos WHERE matricula=?";
}
