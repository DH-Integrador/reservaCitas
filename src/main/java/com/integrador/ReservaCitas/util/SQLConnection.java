package com.integrador.ReservaCitas.util;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLConnection {
    private final static String DB_JDBC_DRIVER = "org.h2.Driver";
    private final static String DB_URL = "jdbc:h2:~/reserva";
    private final static String DB_USER ="sa";
    private final static String DB_PASSWORD = "";
    private static Connection connection;
    private static final Logger logger = Logger.getLogger(SQLConnection.class);

    public static Connection createConnection(){
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            logger.info("Conexión exitosa con la base de datos");
            return connection;
        }catch (Exception e){
            logger.error("Error al conectar la base de datos", e);
        }
        return null;
    }

    public static Connection getConnection() {
        if(connection == null)
            return createConnection();
        else return connection;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            logger.info("Conexión cerrada exitosamente.");
        }
    }

    public static void createTables() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(SQLQueries.CREATETABLES_ODONTOLOGO);
        statement.execute(SQLQueries.CREATETABLES_PACIENTE);
        statement.execute(SQLQueries.CREATETABLES_DOMICILIO);
        statement.close();
    }
}
