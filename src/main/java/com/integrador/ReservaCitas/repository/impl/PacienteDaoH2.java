package com.integrador.ReservaCitas.repository.impl;

import com.integrador.ReservaCitas.repository.IDao;
import com.integrador.ReservaCitas.model.Domicilio;
import com.integrador.ReservaCitas.model.Paciente;
import com.integrador.ReservaCitas.util.SQLConnection;
import com.integrador.ReservaCitas.util.SQLQueries;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.ArrayList;

@Repository
public class PacienteDaoH2 implements IDao<Paciente> {
    private PacienteDaoH2 pacienteDaoH2;
    private List<Paciente> pacienteList = new ArrayList<>();
    private DomicilioDaoH2 domicilioDaoH2 = new DomicilioDaoH2();
    private static final Logger logger = Logger.getLogger(PacienteDaoH2.class);
    private Connection connection;

    public PacienteDaoH2() {
        connection = SQLConnection.getConnection();
    }

    @Override
    public Paciente guardar(Paciente paciente) throws Exception {
        try(PreparedStatement statement = connection.prepareStatement(SQLQueries.INSERTCUSTOM_PACIENTE)){
            connection.setAutoCommit(false);
            statement.setString(1, paciente.getDni());
            statement.setString(2, paciente.getNombre());
            statement.setString(3, paciente.getApellido());
            java.util.Date utilDate = paciente.getFechaAlta();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            statement.setDate(4, sqlDate);
            Domicilio domicilio = domicilioDaoH2.guardar(paciente.getDomicilio());
            paciente.getDomicilio().setId(domicilio.getId());
            statement.setInt(5, paciente.getDomicilio().getId());
            statement.execute();
            connection.commit();
            logger.info("Se guardó el Paciente : " + paciente.getNombre() + paciente.getApellido());
        } catch(Exception e){
            connection.rollback();
            logger.error("No se pudo persistir: " + paciente, e);
            throw new RuntimeException("Sucedió un error al persistir", e);
        } finally {
            connection.setAutoCommit(true);
        }
        return paciente;
    }

    @Override
    public void eliminar(String Dni) throws Exception{
        try {
            try (PreparedStatement statement = connection.prepareStatement(SQLQueries.DELETE_PACIENTE)) {
            statement.setString(1, Dni);
            int rowsAffected = statement.executeUpdate();
            if(rowsAffected > 0)
                logger.info("Paciente con Dni " + Dni + " eliminado correctamente.");
            else {
                logger.info("No existe ningún Paciente con Dni: " +  Dni);
                throw new RuntimeException("No se pudo encontrar el Paciente");
                }
            }
        } catch (Exception e){
            logger.error("Error al eliminar el Paciente con Dni: " + Dni);
            throw new RuntimeException("Error al eliminar el Paciente ", e);
        }
    }

    @Override
    public Paciente buscar(String Dni) throws Exception {
    try(PreparedStatement statement = connection.prepareStatement(SQLQueries.SELECT_PACIENTE)){
        statement.setString(1, Dni);
        ResultSet resultSet = statement.executeQuery();
        resultSet.last();
        if(resultSet.getRow()==1){
            Paciente paciente = new Paciente();
            paciente.setDni(resultSet.getString(1));
            paciente.setNombre(resultSet.getString(2));
            paciente.setApellido(resultSet.getString(3));
            paciente.setFechaAlta(resultSet.getDate(4));
            String idDomicilio = Integer.toString(resultSet.getInt(5));
            Domicilio domicilio = domicilioDaoH2.buscar(idDomicilio);
            paciente.setDomicilio(domicilio);
            return paciente;
        }
        else throw new RuntimeException("No se pudo encontrar el Paciente");
    } catch (Exception e){
        logger.error("No se pudo encontrar el Paciente con Dni: " + Dni, e);
        throw new RuntimeException("Error al buscar el Paciente");
    }
    }

    @Override
    public List<Paciente> buscarTodos() throws SQLException {
    try (PreparedStatement statement = connection.prepareStatement(SQLQueries.SELECTALL_PACIENTES)){
        ResultSet resultSet = statement.executeQuery();
        pacienteList.clear();
        while(resultSet.next()){
            Paciente paciente = new Paciente();
            paciente.setDni(resultSet.getString("DNI"));
            paciente.setNombre(resultSet.getString("NOMBRE"));
            paciente.setApellido(resultSet.getString("APELLIDO"));
            paciente.setFechaAlta(resultSet.getDate("FECHA_ALTA"));
            String idDomicilio = Integer.toString(resultSet.getInt("DOMICILIO_ID"));
            Domicilio domicilio = domicilioDaoH2.buscar(idDomicilio);
            paciente.setDomicilio(domicilio);
            pacienteList.add(paciente);
        }

    } catch (Exception e){
        logger.error("Error al buscar todos los Pacientes ", e);
        throw new RuntimeException("Error al buscar todos los Pacientes", e);
    }
        return pacienteList;
    }

    @Override
    public Paciente actualizar(Paciente paciente) throws Exception {
    try(PreparedStatement statement = connection.prepareStatement(SQLQueries.UPDATECUSTOM_PACIENTE)){
        connection.setAutoCommit(false);
        statement.setString(1, paciente.getDni());
        statement.setString(2, paciente.getNombre());
        statement.executeUpdate();
        connection.commit();
        logger.info("Se actualizó el Paciente con Dni: " + paciente.getDni());
    } catch (Exception e){
        connection.rollback();
        logger.error("No se pudo actualizar: " + paciente, e);
        throw new RuntimeException("No se pudo actualizar el Paciente ", e);
    } finally {
        connection.setAutoCommit(true);
    }
        return paciente;
    }
}
