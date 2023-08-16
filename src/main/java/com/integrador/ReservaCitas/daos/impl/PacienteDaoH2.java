package com.integrador.ReservaCitas.daos.impl;

import com.integrador.ReservaCitas.daos.IDao;
import com.integrador.ReservaCitas.models.Odontologo;
import com.integrador.ReservaCitas.models.Paciente;

import java.sql.SQLException;
import java.util.List;

public class PacienteDaoH2 implements IDao<Paciente> {
    private PacienteDaoH2 pacienteDaoH2;
    private List<Paciente> pacienteList = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(PacienteDaoH2.class);
    private Connection connection;

    public PacienteDaoH2() {
        connection = SQLConnection.getConnection();
    }

    @Override
    public Paciente guardar(Paciente paciente) throws Exception {
        try(PreparedStatement statement = connection.prepareStatement(SQLQueries.INSERT_CUSTOM)){
        connection.setAutoCommit(false);
        statement.setString(1, paciente.getDni());
        statement.setString(2, paciente.getNombre());
        statement.setString(3, paciente.getApellido());
        statement.setDate(4,paciente.getFechaAlta());
        statement.setString(5, paciente.getDomicilio());
        statement.execute();
        connection.commit();
        logger.info("Se guardó el Paciente : " + paciente.getNombre + paciente.getApellido());
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
        try (PreparedStatement statement = connection.prepareStatement(SQLQueries.DELETE)) {
        statement.setString(1, Dni);
        int rowsAffected = statement.executeUpdate();
        if(rowsAffected > 0)
            logger.info("Paciente con Dni " + Dni + " eliminado correctamente.");
        else {
            logger.info("No existe ningún Paciente con Dni: " +  Dni);
            throw new RuntimeException("No se pudo encontrar el Paciente");
        }
    }
    } catch (Exception e) {
        logger.error("Error al eliminar el Paciente con Dni: " + Dni);
        throw new RuntimeException("Error al eliminar el Paciente ", e);
    }

    }

    @Override
    public Paciente buscar(String Dni) throws Exception {
    try(PreparedStatement statement = connection.prepareStatement(SQLQueries.SELECT)){
        statement.setString(1, Dni);
        ResultSet resultSet = statement.executeQuery();
        resultSet.last();
        if(resultSet.getRow()==1){
            Paciente paciente = new Paciente();
            paciente.setDni(resultSet.getString(1));
            paciente.setNombre(resultSet.getString(2));
            paciente.setApellido(resultSet.getString(3));
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
    try (PreparedStatement statement = connection.prepareStatement(SQLQueries.SELECT_ALL)){
        ResultSet resultSet = statement.executeQuery();
        pacienteList.clear();
        while(resultSet.next()){
            Paciente paciente = new Paciente();
            paciente.setDni(resultSet.getString("DNI"));
            paciente.setNombre(resultSet.getString("NOMBRE"));
            paciente.setApellido(resultSet.getString("APELLIDO"));
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
    try(PreparedStatement statement = connection.prepareStatement(SQLQueries.UPDATE_CUSTOM)){
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
