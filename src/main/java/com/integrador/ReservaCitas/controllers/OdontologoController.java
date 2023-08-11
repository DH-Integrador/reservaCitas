package com.integrador.ReservaCitas.controllers;

import com.integrador.ReservaCitas.models.Odontologo;
import com.integrador.ReservaCitas.services.impl.OdontologoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class OdontologoController {

    private final OdontologoService odontologoService;
    private static final Logger logger = Logger.getLogger(OdontologoController.class);

    @Autowired
    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @GetMapping("/odontologos")
    public List<Odontologo> getOdontologos() throws SQLException {
        return odontologoService.buscarTodos();
    }

    @PostMapping("/register")
    public Odontologo guardar(@RequestBody Odontologo odontologo) throws Exception {
        Odontologo odontologoGuardado = null;
        try{
           odontologoGuardado = odontologoService.guardar(odontologo);
        }catch(Exception e){
            logger.error("Error al guardar el Odontólogo: " + e);
        }
        return odontologoGuardado;
    }

    @GetMapping("/odontologos/{matricula}")
    public Odontologo getOdontologoByMatricula(@PathVariable String matricula) throws Exception {
        Odontologo odontologoEncontrado = null;
        try {
            odontologoEncontrado = odontologoService.buscar(matricula);
        } catch (Exception e){
            logger.error("Error al obtener el Odontólogo con la matrícula: " + matricula + e);
        }
        return odontologoEncontrado;
    }

    @PutMapping("/odontologos/{matricula}")
    public Odontologo actualizarOdontologo(@PathVariable String matricula, @RequestBody Odontologo odontologo){
        Odontologo odontologoActualizado = null;
        try {
            Odontologo odontologoExistente = odontologoService.buscar(matricula);
            if(odontologoExistente != null) {
                odontologo.setMatricula(matricula);
                odontologoActualizado = odontologoService.actualizar(odontologo);
            }
        } catch (Exception e){
            logger.error("Error al actualizar el Odontólogo: " + e);
        }
        return odontologoActualizado;
    }

    @DeleteMapping("/odontologos/{matricula}")
    public void eliminarOdontologo(@PathVariable String matricula){
        try{
            odontologoService.eliminar(matricula);
        } catch(Exception e){
            logger.error("Error al eliminar el Odontólogo con la matrícula: " + matricula + e);
        }
    }
}
