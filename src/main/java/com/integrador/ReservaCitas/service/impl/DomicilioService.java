package com.integrador.ReservaCitas.service.impl;

import com.integrador.ReservaCitas.entity.Domicilio;
import com.integrador.ReservaCitas.repository.DomicilioRepository;
import com.integrador.ReservaCitas.service.IService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DomicilioService implements IService<Domicilio> {

    private final DomicilioRepository domicilioRepository;
    private static final Logger logger = Logger.getLogger(DomicilioService.class);

    @Autowired
    public DomicilioService(DomicilioRepository domicilioIDao) {
        this.domicilioRepository = domicilioIDao;
    }

    @Override
    public Domicilio guardar(Domicilio domicilio) throws DataAccessException {
        try{
            Domicilio domicilioGuardado = domicilioRepository.save(domicilio);
            logger.info("Domicilio con id " + domicilio.getId() + " creado correctamente");
            return domicilioGuardado;
        } catch(DataAccessException e){
            logger.error("Error al guardar el domicilio: " + domicilio, e);
            throw  new DataAccessException("Error al guardar el domicilio", e) {};
        }
    }

    @Override
    public void eliminar(String id) throws DataAccessException {

    }

    @Override
    public Domicilio actualizar(Domicilio domicilio) throws DataAccessException {
        return null;
    }

    @Override
    public Domicilio buscar(String id) throws DataAccessException {
        try {
            int idD = Integer.parseInt(id);
            return domicilioRepository.findById(idD).orElse(null);
        }
        catch (DataAccessException e) {
            logger.error("Error al buscar el domicilio con id: " + id, e);
            throw new RuntimeException("Error al buscar el domicilio con id: " + id, e);
        }
    }

    @Override
    public List<Domicilio> buscarTodos() throws DataAccessException {
        return null;
    }
}
