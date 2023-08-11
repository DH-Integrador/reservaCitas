package com.integrador.ReservaCitas.services.impl;

import com.integrador.ReservaCitas.daos.IDao;
import com.integrador.ReservaCitas.models.Odontologo;
import com.integrador.ReservaCitas.services.IService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class OdontologoService implements IService<Odontologo> {
    private IDao<Odontologo> odontologoIDao;
    private static final Logger logger = Logger.getLogger(OdontologoService.class);

    public OdontologoService() {
    }

    @Autowired
    public OdontologoService(IDao<Odontologo> odontologoIDao) {
        this.odontologoIDao = odontologoIDao;
    }

    @Override
    public Odontologo guardar(Odontologo odontologo) throws Exception {
        Odontologo odontologoGuardado = null;
        try{
            odontologoGuardado = odontologoIDao.guardar(odontologo);
        } catch(Exception ignored){}
        return odontologoGuardado;
    }
    @Override
    public void eliminar(String matricula){
        try{
            odontologoIDao.eliminar(matricula);
        } catch(Exception ignored){}
    }

    @Override
    public Odontologo buscar(String matricula) throws Exception {
        Odontologo odontologoEncontrado = null;
        try{
            odontologoEncontrado = odontologoIDao.buscar(matricula);
        } catch(Exception ignored){}
        return odontologoEncontrado;
    }

    @Override
    public List<Odontologo> buscarTodos() throws SQLException {
        List<Odontologo> odontologos = null;
        try{
            odontologos = odontologoIDao.buscarTodos();
        } catch (Exception ignored){}
        return odontologos;
    }

    @Override
    public Odontologo actualizar(Odontologo odontologo) throws Exception {
        Odontologo odontologoActualizado = null;
        try {
            odontologoActualizado = odontologoIDao.actualizar(odontologo);
        } catch (Exception ignored) {
        }
        return odontologoActualizado;
    }
}
