package com.integrador.ReservaCitas.service.impl;

import com.integrador.ReservaCitas.model.Domicilio;
import com.integrador.ReservaCitas.repository.IDao;
import com.integrador.ReservaCitas.service.IService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class DomicilioService implements IService<Domicilio> {

    private IDao<Domicilio> domicilioIDao;
    private static final Logger logger = Logger.getLogger(DomicilioService.class);

    public DomicilioService() {
    }

    @Autowired
    public DomicilioService(IDao<Domicilio> domicilioIDao) {
        this.domicilioIDao = domicilioIDao;
    }

    @Override
    public Domicilio guardar(Domicilio domicilio) throws Exception {
        try{
            Domicilio domicilioGuardado = domicilioIDao.guardar(domicilio);
            logger.info("Domicilio con id " + domicilio.getId() + " creado correctamente");
            return domicilioGuardado;
        } catch(Exception e){
            logger.error("Error al guardar el domicilio: " + domicilio, e);
            throw  new RuntimeException("Error al guardar el domicilio", e);
        }
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
        try {
            return domicilioIDao.buscar(id);
        }
        catch (Exception e) {
            logger.error("Error al buscar el domicilio con id: " + id, e);
            throw new RuntimeException("Error al buscar el domicilio con id: " + id, e);
        }
    }

    @Override
    public List<Domicilio> buscarTodos() throws SQLException {
        return null;
    }
}
