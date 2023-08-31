package com.integrador.ReservaCitas.service.impl;

import com.integrador.ReservaCitas.entity.Odontologo;
import com.integrador.ReservaCitas.entity.Turno;
import com.integrador.ReservaCitas.repository.OdontologoRepository;
import com.integrador.ReservaCitas.service.IService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OdontologoService implements IService<Odontologo>{
    private final OdontologoRepository odontologoRepository;
    private static final Logger logger = Logger.getLogger(OdontologoService.class);

    @Autowired
    public OdontologoService(OdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    @Override
    public Odontologo guardar(Odontologo odontologo) throws DataAccessException {
        try{
            Odontologo odontologoGuardado = odontologoRepository.save(odontologo);
            logger.info("Odontólogo con matrícula " + odontologo.getMatricula() + " creado correctamente");
            return odontologoGuardado;
        } catch(DataAccessException e){
            logger.error("Error al guardar el odontólogo: " + odontologo, e);
            throw  new DataAccessException("Error al guardar el odontólogo", e) {};
        }
    }
    @Override
    public void eliminar(String matricula) throws DataAccessException{
        try{
            odontologoRepository.deleteById(matricula);
            logger.info("Odontólogo con matrícula " + matricula + " eliminado correctamente");
        } catch(EmptyResultDataAccessException e){
            logger.error("No se encontró el odontólogo con matrícula: " + matricula, e);
        } catch (DataAccessException e){
            logger.error("Error al eliminar el odontólogo con matrícula: " + matricula, e);
            throw new DataAccessException("Error al eliminar el odontólogo", e) {};
        }
    }

    @Override
    public Odontologo buscar(String matricula) throws DataAccessException {
        try{
            Odontologo odontologoBuscado = odontologoRepository.findById(matricula).orElse(null);
            if(odontologoBuscado == null)
                throw new DataAccessException("No se encontró el odontólogo con matrícula: " + matricula) {};
            return odontologoBuscado;
        } catch(DataAccessException e){
            logger.error("No se pudo encontrar el odontólogo con matrícula: " + matricula, e);
            throw new DataAccessException("Error al buscar el odontólogo", e) {};
        }
    }

    @Override
    public List<Odontologo> buscarTodos() throws DataAccessException{
        try{
            List<Odontologo> odontologos = odontologoRepository.findAll();
            if(odontologos.isEmpty())
                logger.error("No se encontraron odontólogos");
            return odontologos;
        } catch (DataAccessException e){
            logger.error("Error al buscar todos los odontólogos", e);
            throw new DataAccessException("Error al buscar todos los odontólogos", e) {};
        }
    }

    @Override
    public Odontologo actualizar(Odontologo odontologo) throws DataAccessException {
        try {
            Odontologo odontologoActualizado = odontologoRepository.save(odontologo);
            logger.info("Se actualizó el odontólogo con matrícula: " + odontologo.getMatricula());
            return odontologoActualizado;
        } catch (DataAccessException e) {
            logger.error("No se pudo actualizar: " + odontologo, e);
            throw new DataAccessException("No se pudo actualizar el odontólogo", e) {};
        }
    }
}
