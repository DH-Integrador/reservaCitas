package com.integrador.ReservaCitas.service.impl;

import com.integrador.ReservaCitas.model.Paciente;
import com.integrador.ReservaCitas.repository.IDao;
import com.integrador.ReservaCitas.service.IService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Service
public class PacienteService implements IService<Paciente> {
    private IDao<Paciente> pacienteIDao;
    private static final Logger logger = Logger.getLogger(PacienteService.class);

    public PacienteService() {
    }
    @Autowired
    public PacienteService(IDao<Paciente> pacienteIDao) {
        this.pacienteIDao = pacienteIDao;
    }
    @Override
    public Paciente guardar(Paciente paciente) throws Exception {
        try{
            paciente.setFechaAlta(new Date());
            Paciente pacienteGuardado = pacienteIDao.guardar(paciente);
            logger.info("Paciente con id " + paciente.getDni() + " creado correctamente");
            return pacienteGuardado;
        } catch(Exception e){
            logger.error("Error al guardar el paciente: " + paciente, e);
            throw  new RuntimeException("Error al guardar el paciente", e);
        }
    }

    @Override
    public void eliminar(String dni) throws Exception{
        try{
            pacienteIDao.eliminar(dni);
            logger.info("Paciente con id " + dni + " eliminado correctamente");
        } catch(Exception e){
            logger.error("Error al eliminar el paciente con id: " + dni, e);
            throw new RuntimeException("Error al eliminar el paciente", e);
        }
    }

    @Override
    public Paciente buscar(String dni) throws Exception {
        try{
            Paciente pacienteBuscado = pacienteIDao.buscar(dni);
            if(pacienteBuscado == null)
                throw new RuntimeException("No se encontró el paciente con id: " + dni);
            return pacienteBuscado;
        } catch(Exception e){
            logger.error("No se pudo encontrar el paciente con id: " + dni, e);
            throw new RuntimeException("Error al buscar el paciente", e);
        }
    }

    @Override
    public List<Paciente> buscarTodos() throws SQLException {
        try{
            List<Paciente> pacientes = pacienteIDao.buscarTodos();
            if(pacientes.isEmpty())
                throw new RuntimeException("No se encontraron pacientes");
            return pacientes;
        } catch(Exception e){
            logger.error("No se pudo encontrar ningún paciente", e);
            throw new RuntimeException("Error al buscar los pacientes", e);
        }
    }

    @Override
    public Paciente actualizar(Paciente paciente) throws Exception {
        try {
            Paciente pacienteActualizado = pacienteIDao.actualizar(paciente);
            logger.info("Paciente con id " + paciente.getDni() + " actualizado correctamente");
            return pacienteActualizado;
        } catch (Exception e){
            logger.error("Error al actualizar el paciente con id: " + paciente.getDni(), e);
            throw new RuntimeException("Error al actualizar el paciente", e);
        }
    }
}
