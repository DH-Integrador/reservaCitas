package com.integrador.ReservaCitas.repository.impl;

import com.integrador.ReservaCitas.repository.IDao;
import com.integrador.ReservaCitas.model.Odontologo;

import com.integrador.ReservaCitas.util.SQLConnection;
import com.integrador.ReservaCitas.util.SQLQueries;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OdontologoDaoH2 implements IDao<Odontologo> {

    private OdontologoDaoH2 odontologoDaoH2;
    private List<Odontologo> odontologosRepositorio = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(OdontologoDaoH2.class);
    private Connection connection;


    public OdontologoDaoH2() {
        connection = SQLConnection.getConnection();
    }

    @Override
    public Odontologo guardar(Odontologo odontologo) throws Exception {
        try(PreparedStatement statement = connection.prepareStatement(SQLQueries.INSERTCUSTOM_ODONTOLOGO)){
            connection.setAutoCommit(false);
            statement.setString(1, odontologo.getMatricula());
            statement.setString(2, odontologo.getNombre());
            statement.setString(3, odontologo.getApellido());
            statement.execute();
            connection.commit();
            logger.info("Se guardó el odontólogo con matrícula: " + odontologo.getMatricula());
        } catch(Exception e){
            connection.rollback();
            logger.error("No se pudo persistir: " + odontologo, e);
            throw new RuntimeException("Sucedió un error al persistir", e);
        } finally {
            connection.setAutoCommit(true);
        }
        return odontologo;
    }

    @Override
    public void eliminar(String matricula) throws Exception{
        try {
            try (PreparedStatement statement = connection.prepareStatement(SQLQueries.DELETE_ODONTOLOGO)) {
                statement.setString(1, matricula);
                int rowsAffected = statement.executeUpdate();
                if(rowsAffected > 0)
                    logger.info("Odontólogo con matrícula " + matricula + " eliminado correctamente.");
                else {
                    logger.info("No existe ningún odontólogo con matrícula: " +  matricula);
                    throw new RuntimeException("No se pudo encontrar el odontólogo");
                }
            }
        } catch (Exception e) {
            logger.error("Error al eliminar el odontólogo con matrícula: " + matricula);
            throw new RuntimeException("Error al eliminar el odontólogo ", e);
        }
    }

    @Override
    public Odontologo buscar(String matricula) throws Exception {
        try(PreparedStatement statement = connection.prepareStatement(SQLQueries.SELECT_ODONTOLOGO)){
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
            else throw new RuntimeException("No se pudo encontrar el odontólogo");
        } catch (Exception e){
            logger.error("No se pudo encontrar el odontólogo con matrícula: " + matricula, e);
            throw new RuntimeException("Error al buscar el odontólogo");
        }
    }

    @Override
    public List<Odontologo> buscarTodos() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SQLQueries.SELECTALL_ODONTOLOGOS)){
            ResultSet resultSet = statement.executeQuery();
            odontologosRepositorio.clear();
            while(resultSet.next()){
                Odontologo odontologo = new Odontologo();
                odontologo.setMatricula(resultSet.getString("MATRICULA"));
                odontologo.setNombre(resultSet.getString("NOMBRE"));
                odontologo.setApellido(resultSet.getString("APELLIDO"));
                odontologosRepositorio.add(odontologo);
            }

        } catch (Exception e){
            logger.error("Error al buscar todos los odontólogos ", e);
            throw new RuntimeException("Error al buscar todos los odontólogos", e);
        }
        return odontologosRepositorio;
    }

    @Override
    public Odontologo actualizar(Odontologo odontologo) throws Exception {
        try(PreparedStatement statement = connection.prepareStatement(SQLQueries.UPDATECUSTOM_ODONTOLOGO)){
            connection.setAutoCommit(false);
            statement.setString(1, odontologo.getNombre());
            statement.setString(2, odontologo.getMatricula());
            statement.executeUpdate();
            connection.commit();
            logger.info("Se actualizó el odontólogo con matrícula: " + odontologo.getMatricula());
        } catch (Exception e){
            connection.rollback();
            logger.error("No se pudo actualizar: " + odontologo, e);
            throw new RuntimeException("No se pudo actualizar el odontólogo ", e);
        } finally {
            connection.setAutoCommit(true);
        }
        return odontologo;
    }
}
