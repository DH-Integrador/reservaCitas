package com.integrador.ReservaCitas.service.impl;

import com.integrador.ReservaCitas.entity.Odontologo;
import com.integrador.ReservaCitas.entity.Paciente;
import com.integrador.ReservaCitas.entity.Turno;
import com.integrador.ReservaCitas.repository.TurnoRepository;
import com.integrador.ReservaCitas.service.IService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class TurnoService implements IService<Turno> {

    private final TurnoRepository turnoRepository;

    private static final Logger logger = Logger.getLogger(TurnoService.class);

    @Autowired
    public TurnoService(TurnoRepository turnoRepository) {
        this.turnoRepository = turnoRepository;
    }
    @Override
    public Turno guardar(Turno turno) throws DataAccessException {
        try{
            Turno turnoGuardado = turnoRepository.save(turno);
            logger.info("Turno con id " + turno.getId() + " creado correctamente");
            return turnoGuardado;
        } catch(DataAccessException e){
            logger.error("Error al guardar el turno: " + turno, e);
            throw  new DataAccessException("Error al guardar el turno", e) {};
        }
    }

    public Turno guardar(Odontologo odontologo, Paciente paciente, Turno turno){
        try{
            turno.setOdontologo(odontologo);
            turno.setPaciente(paciente);
            Turno turnoGuardado = turnoRepository.save(turno);
            logger.info("Turno con id " + turno.getId() + " creado correctamente");
            return turnoGuardado;
        } catch(DataAccessException e){
            logger.error("Error al guardar el turno: " + turno, e);
            throw  new DataAccessException("Error al guardar el turno", e) {};
        }
    }

    @Override
    public void eliminar(String id) throws DataAccessException{
        try{
            Long idT = Long.parseLong(id);
            turnoRepository.deleteById(idT);
            logger.info("Turno con id " + id + " eliminado correctamente");
        } catch(DataAccessException e){
            logger.error("Error al eliminar el turno con id: " + id, e);
            throw new DataAccessException("Error al eliminar el turno", e) {};
        }
    }

    @Override
    public Turno buscar(String id) throws Exception {
        try{
            Long idT = Long.parseLong(id);
            Turno turnoBuscado = turnoRepository.findById(idT).orElse(null);
            if(turnoBuscado == null)
                throw new DataAccessException("No se encontró el turno con id: " + id) {};
            return turnoBuscado;
        } catch(DataAccessException e){
            logger.error("No se pudo encontrar el turno con id: " + id, e);
            throw new DataAccessException("Error al buscar el turno", e) {};
        }
    }

    @Override
    public List<Turno> buscarTodos() throws DataAccessException {
        try{
            List<Turno> turnos = turnoRepository.findAll();
            if(turnos.isEmpty())
                throw new DataAccessException("No se encontraron turnos") {};
            return turnos;
        } catch(DataAccessException e){
            logger.error("No se pudo encontrar ningún turno", e);
            throw new DataAccessException("Error al buscar los turnos", e) {};
        }
    }

    @Override
    public Turno actualizar(Turno turno) throws DataAccessException {
        try{
            Turno turnoActualizado = turnoRepository.save(turno);
            logger.info("Turno con id " + turno.getId() + " actualizado correctamente");
            return turnoActualizado;
        } catch(DataAccessException e){
            logger.error("Error al actualizar el turno con id: " + turno.getId(), e);
            throw new DataAccessException("Error al actualizar el turno", e) {};
        }
    }
}
