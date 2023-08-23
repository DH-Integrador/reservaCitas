package com.integrador.ReservaCitas.repository.impl;

import com.integrador.ReservaCitas.repository.IDao;
import com.integrador.ReservaCitas.model.Domicilio;
import com.integrador.ReservaCitas.util.SQLConnection;
import com.integrador.ReservaCitas.util.SQLQueries;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DomicilioDaoH2 implements IDao<Domicilio> {

    private DomicilioDaoH2 domicilioDaoH2;
    private List<Domicilio> domicilioList = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(DomicilioDaoH2.class);
    private Connection connection;

    public DomicilioDaoH2() {
        connection = SQLConnection.getConnection();
    }

    @Override
    public Domicilio guardar(Domicilio domicilio) throws Exception {
        try(PreparedStatement statement = connection.prepareStatement(SQLQueries.INSERTCUSTOM_DOMICILIO)){
            connection.setAutoCommit(false);
            statement.setInt(1, domicilio.getId());
            statement.setString(2, domicilio.getCalle());
            statement.setString(3, domicilio.getNumero());
            statement.setString(4, domicilio.getLocalidad());
            statement.setString(5, domicilio.getProvincia());
            statement.execute();
            connection.commit();
            logger.info("Se guardó el domicilio: " + domicilio.getCalle() + domicilio.getNumero());
        } catch(Exception e){
            connection.rollback();
            logger.error("No se pudo persistir: " + domicilio, e);
            throw new RuntimeException("Sucedió un error al persistir", e);
        } finally {
            connection.setAutoCommit(true);
        }
        return domicilio;
    }

    @Override
    public void eliminar(String id) throws Exception {

    }

    @Override
    public Domicilio actualizar(Domicilio domicilio) throws Exception {
        return null;
    }

    @Override
    public Domicilio buscar(String id) throws Exception {
        try(PreparedStatement statement = connection.prepareStatement(SQLQueries.SELECT_DOMICILIO)){
            statement.setInt(1, Integer.parseInt(id));
            ResultSet resultSet = statement.executeQuery();
            resultSet.last();
            if(resultSet.getRow()==1){
                Domicilio domicilio = new Domicilio();
                domicilio.setCalle(resultSet.getString("calle"));
                domicilio.setNumero(resultSet.getString("numero"));
                domicilio.setLocalidad(resultSet.getString("localidad"));
                domicilio.setProvincia(resultSet.getString("provincia"));
                return domicilio;
            }
            else throw new RuntimeException("No se pudo encontrar un domicilio");
        } catch(Exception e){
            logger.error("No se pudo buscar el domicilio con id: " + id, e);
            throw new RuntimeException("Sucedió un error al buscar", e);
        }
    }

    @Override
    public List<Domicilio> buscarTodos() throws SQLException {
        return null;
    }
}
