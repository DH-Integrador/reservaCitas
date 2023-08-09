package com.integrador.ReservaCitas.daos.impl;

import com.integrador.ReservaCitas.daos.IDao;
import com.integrador.ReservaCitas.models.Odontologo;

import com.integrador.ReservaCitas.utils.SQLConnection;
import com.integrador.ReservaCitas.utils.SQLQueries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OdontologoDaoH2 implements IDao<Odontologo> {

    private OdontologoDaoH2 odontologoDaoH2;
    private List<Odontologo> odontologosRepositorio = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(OdontologoDaoH2.class);

    public OdontologoDaoH2(OdontologoDaoH2 odontologoDaoH2) {
        this.odontologoDaoH2 = odontologoDaoH2;
    }

    @Override
    public Odontologo guardar(Odontologo odontologo) throws Exception {
        try(PreparedStatement statement = SQLConnection.getConnection().prepareStatement(SQLQueries.INSERT_CUSTOM)){
            SQLConnection.getConnection().setAutoCommit(false);
            statement.setString(1, odontologo.getMatricula());
            statement.setString(2, odontologo.getNombre());
            statement.setString(3, odontologo.getApellido());
            statement.execute();
            SQLConnection.getConnection().commit();
            logger.info("Se guardó el odontólogo con matrícula: " + odontologo.getMatricula());
        } catch(Exception e){
            SQLConnection.getConnection().rollback();
            logger.error("No se pudo persistir: " + odontologo, e);
            throw new Exception("Sucedió un error al persistir");
        }
        SQLConnection.getConnection().setAutoCommit(true);
        return odontologo;
    }

    @Override
    public void eliminar(String matricula) {
        try {
            if (buscar(matricula) != null) {
                try (PreparedStatement statement = SQLConnection.getConnection().prepareStatement(SQLQueries.DELETE)) {
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
        try(PreparedStatement statement = SQLConnection.getConnection().prepareStatement(SQLQueries.SELECT)){
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
        Statement statement = SQLConnection.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(SQLQueries.SELECT_ALL);
        while(resultSet.next()){
            Odontologo odontologo = new Odontologo();
            odontologo.setMatricula(resultSet.getString("MATRICULA"));
            odontologo.setNombre(resultSet.getString("NOMBRE"));
            odontologo.setApellido(resultSet.getString("APELLIDO"));
            odontologosRepositorio.add(odontologo);
        }
        return odontologosRepositorio;
    }
}
