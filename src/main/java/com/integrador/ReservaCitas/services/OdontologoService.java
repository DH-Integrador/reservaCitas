package com.integrador.ReservaCitas.services;

import com.integrador.ReservaCitas.daos.IDao;
import com.integrador.ReservaCitas.models.Odontologo;
import com.integrador.ReservaCitas.utils.SQLConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class OdontologoService {
    private IDao<Odontologo> odontologoIDao;
    private static final Logger logger = LogManager.getLogger(OdontologoService.class);

    public OdontologoService() {
    }

    public OdontologoService(IDao<Odontologo> odontologoIDao) {
        this.odontologoIDao = odontologoIDao;
    }

    public Odontologo guardarOdontologo(Odontologo odontologo) throws Exception {
        try{
            odontologoIDao.guardar(odontologo);
        } catch(Exception ignored){}
        return odontologo;
    }

    public void eliminarOdontologo(String matricula){
        try{
            odontologoIDao.eliminar(matricula);
        } catch(Exception ignored){}
    }

    public Odontologo buscar(String matricula) throws Exception {
        Odontologo odontologo = null;
        try{
            odontologo = odontologoIDao.buscar(matricula);
        } catch(Exception ignored){}
        return odontologo;
    }

    public List<Odontologo> buscarTodos() throws SQLException {
        try{
            return odontologoIDao.buscarTodos();
        } catch (Exception ignored){}
        return null;
    }
}
