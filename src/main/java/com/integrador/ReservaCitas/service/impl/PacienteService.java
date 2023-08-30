package com.integrador.ReservaCitas.service.impl;

import com.integrador.ReservaCitas.entity.Paciente;
import com.integrador.ReservaCitas.repository.PacienteRespository;
import com.integrador.ReservaCitas.service.IService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PacienteService implements IService<Paciente> {
    private final PacienteRespository pacienteRespository;
    private static final Logger logger = Logger.getLogger(PacienteService.class);

    @Autowired
    public PacienteService(PacienteRespository pacienteIDao) {
        this.pacienteRespository = pacienteIDao;
    }
    @Override
    public Paciente guardar(Paciente paciente) throws DataAccessException {
        try{
            paciente.setFechaAlta(new Date());
            Paciente pacienteGuardado = pacienteRespository.save(paciente);
            logger.info("Paciente con id " + paciente.getDni() + " creado correctamente");
            return pacienteGuardado;
        } catch(DataAccessException e){
            logger.error("Error al guardar el paciente: " + paciente, e);
            throw  new DataAccessException("Error al guardar el paciente", e) {};
        }
    }

    @Override
    public void eliminar(String dni) throws DataAccessException{
        try{
            pacienteRespository.deleteById(dni);
            logger.info("Paciente con id " + dni + " eliminado correctamente");
        } catch(DataAccessException e){
            logger.error("Error al eliminar el paciente con id: " + dni, e);
            throw new DataAccessException("Error al eliminar el paciente", e) {};
        }
    }

    @Override
    public Paciente buscar(String dni) throws DataAccessException {
        try{
            Paciente pacienteBuscado = pacienteRespository.findById(dni).orElse(null);
            if(pacienteBuscado == null)
                throw new DataAccessException("No se encontró el paciente con id: " + dni) {};
            return pacienteBuscado;
        } catch(DataAccessException e){
            logger.error("No se pudo encontrar el paciente con id: " + dni, e);
            throw new DataAccessException("Error al buscar el paciente", e) {};
        }
    }

    @Override
    public List<Paciente> buscarTodos() throws DataAccessException {
        try{
            List<Paciente> pacientes = pacienteRespository.findAll();
            if(pacientes.isEmpty())
                throw new DataAccessException("No se encontraron pacientes") {
                };
            return pacientes;
        } catch(DataAccessException e){
            logger.error("No se pudo encontrar ningún paciente", e);
            throw new DataAccessException("Error al buscar los pacientes", e) {};
        }
    }

    @Override
    public Paciente actualizar(Paciente paciente) throws DataAccessException {
        try {
            Paciente pacienteActualizado = pacienteRespository.save(paciente);
            logger.info("Paciente con id " + paciente.getDni() + " actualizado correctamente");
            return pacienteActualizado;
        } catch (DataAccessException e){
            logger.error("Error al actualizar el paciente con id: " + paciente.getDni(), e);
            throw new DataAccessException("Error al actualizar el paciente", e) {};
        }
    }
}
