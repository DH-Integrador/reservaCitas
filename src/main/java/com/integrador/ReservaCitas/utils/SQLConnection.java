package com.integrador.ReservaCitas.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private static final Logger logger = LogManager.getLogger(SQLConnection.class);

    public SQLConnection(){
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            logger.info("Conexión exitosa con la base de datos");
        }catch (Exception e){
            logger.error("Error al conectar la base de datos", e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            logger.info("Conexión cerrada exitosamente.");
        }
    }

    public static void createTables() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(SQLQueries.CREATETABLES);
        statement.close();
    }
}
