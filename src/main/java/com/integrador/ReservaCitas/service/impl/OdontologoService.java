package com.integrador.ReservaCitas.service.impl;

import com.integrador.ReservaCitas.repository.IDao;
import com.integrador.ReservaCitas.model.Odontologo;
import com.integrador.ReservaCitas.service.IService;
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
        try{
            Odontologo odontologoGuardado = odontologoIDao.guardar(odontologo);
            logger.info("Odontólogo con matrícula " + odontologo.getMatricula() + " creado correctamente");
            return odontologoGuardado;
        } catch(Exception e){
            logger.error("Error al guardar el odontólogo: " + odontologo, e);
            throw  new RuntimeException("Error al guardar el odontólogo", e);
        }
    }
    @Override
    public void eliminar(String matricula){
        try{
            odontologoIDao.eliminar(matricula);
            logger.info("Odontólogo con matrícula " + matricula + " eliminado correctamente");
        } catch(Exception e){
            logger.error("Error al eliminar el odontólogo con matrícula: " + matricula, e);
            throw new RuntimeException("Error al eliminar el odontólogo", e);
        }
    }

    @Override
    public Odontologo buscar(String matricula) throws Exception {
        try{
            Odontologo odontologoBuscado = odontologoIDao.buscar(matricula);
            if(odontologoBuscado == null)
                throw new RuntimeException("No se encontró el odontólogo con matrícula: " + matricula);
            return odontologoBuscado;
        } catch(Exception e){
            logger.error("No se pudo encontrar el odontólogo con matrícula: " + matricula, e);
            throw new RuntimeException("Error al buscar el odontólogo", e);
        }
    }

    @Override
    public List<Odontologo> buscarTodos() throws SQLException {
        try{
            List<Odontologo> odontologos = odontologoIDao.buscarTodos();
            if(odontologos.isEmpty())
                logger.error("No se encontraron odontólogos");
            return odontologos;
        } catch (Exception e){
            logger.error("Error al buscar todos los odontólogos", e);
            throw new RuntimeException("Error al buscar todos los odontólogos", e);
        }
    }

    @Override
    public Odontologo actualizar(Odontologo odontologo) throws Exception {
        try {
            Odontologo odontologoActualizado = odontologoIDao.actualizar(odontologo);
            logger.info("Se actualizó el odontólogo con matrícula: " + odontologo.getMatricula());
            return odontologoActualizado;
        } catch (Exception e) {
            logger.error("No se pudo actualizar: " + odontologo, e);
            throw new RuntimeException("No se pudo actualizar el odontólogo", e);
        }

    }
}
