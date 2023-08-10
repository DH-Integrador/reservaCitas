package com.integrador.ReservaCitas.daos.impl;

import com.integrador.ReservaCitas.daos.IDao;
import com.integrador.ReservaCitas.models.Odontologo;

import com.integrador.ReservaCitas.utils.SQLConnection;
import com.integrador.ReservaCitas.utils.SQLQueries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OdontologoDaoH2 implements IDao<Odontologo> {

    private OdontologoDaoH2 odontologoDaoH2;
    private List<Odontologo> odontologosRepositorio = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(OdontologoDaoH2.class);
    private Connection connection;


    public OdontologoDaoH2() {
        connection = SQLConnection.getConnection();
    }

    @Override
    public Odontologo guardar(Odontologo odontologo) throws Exception {
        try {
            connection.setAutoCommit(false);
            try(PreparedStatement statement = connection.prepareStatement(SQLQueries.INSERT_CUSTOM)){
                statement.setString(1, odontologo.getMatricula());
                statement.setString(2, odontologo.getNombre());
                statement.setString(3, odontologo.getApellido());
                statement.execute();
                connection.commit();
                logger.info("Se guardó el odontólogo con matrícula: " + odontologo.getMatricula());
            } catch(Exception e){
                connection.rollback();
                logger.error("No se pudo persistir: " + odontologo, e);
                throw new Exception("Sucedió un error al persistir", e);
            } finally {
                connection.setAutoCommit(true);
            }
        } finally {
            if(connection != null){
                connection.setAutoCommit(true);
            }
        }
        return odontologo;
    }

    @Override
    public void eliminar(String matricula) throws Exception{
        try {
            if (buscar(matricula) != null) {
                try (PreparedStatement statement = connection.prepareStatement(SQLQueries.DELETE)) {
                    statement.setString(1, matricula);
                    statement.executeUpdate();
                    logger.info("Odontólogo con matrícula " + matricula + " eliminado correctamente.");
                } catch (SQLException e) {
                    logger.error("Error al eliminar el odontólogo con matrícula: " + matricula);
                }
            } else {
                logger.info("No existe ningún odontólogo con matrícula: " + matricula);
            }
        } catch (Exception e) {
            logger.error("Error al eliminar el odontólogo con matrícula: " + matricula);
        }
    }

    @Override
    public Odontologo buscar(String matricula) throws Exception {
        try(PreparedStatement statement = connection.prepareStatement(SQLQueries.SELECT)){
            statement.setString(1, matricula);
            ResultSet resultSet = statement.executeQuery();
            resultSet.last();
            if(resultSet.getRow()==1){
                Odontologo odontologo = new Odontologo();
                odontologo.setMatricula(resultSet.getString(1));
                odontologo.setNombre(resultSet.getString(2));
                odontologo.setApellido(resultSet.getString(3));
                return odontologo;
            }
            else throw new Exception("Error al buscar el odontólogo");
        } catch (Exception e){
            logger.error("No se pudo encontrar el odontólogo con matrícula: " + matricula, e);
            throw new Exception("Error al buscar el odontólogo");
        }
    }

    @Override
    public List<Odontologo> buscarTodos() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQLQueries.SELECT_ALL);
        odontologosRepositorio.clear();
        while(resultSet.next()){
            Odontologo odontologo = new Odontologo();
            odontologo.setMatricula(resultSet.getString("MATRICULA"));
            odontologo.setNombre(resultSet.getString("NOMBRE"));
            odontologo.setApellido(resultSet.getString("APELLIDO"));
            odontologosRepositorio.add(odontologo);
        }
        return odontologosRepositorio;
    }

    @Override
    public Odontologo actualizar(Odontologo odontologo) throws Exception {
        try {
            connection.setAutoCommit(false);
            try(PreparedStatement statement = connection.prepareStatement(SQLQueries.UPDATE_CUSTOM)){
                statement.setString(1, odontologo.getNombre());
                statement.setString(2, odontologo.getMatricula());
                statement.executeUpdate();
                connection.commit();
                logger.info("Se actualizó el odontólogo con matrícula: " + odontologo.getMatricula());
            } catch (Exception e){
                connection.rollback();
                logger.error("No se pudo actualizar: " + odontologo, e);
            } finally {
                connection.setAutoCommit(true);
            }
        } finally {
            if(connection != null)
                connection.setAutoCommit(true);
        }
        return odontologo;
    }
}
