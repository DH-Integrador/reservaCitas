package com.integrador.ReservaCitas.controllers;

import com.integrador.ReservaCitas.models.Odontologo;
import com.integrador.ReservaCitas.services.impl.OdontologoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("odontologos")
public class OdontologoController {

    private final OdontologoService odontologoService;
    private static final Logger logger = Logger.getLogger(OdontologoController.class);

    @Autowired
    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @GetMapping
    public ResponseEntity<?> getOdontologos() {
        List<Odontologo> result;
        try {
            List<Odontologo> odontologos = odontologoService.buscarTodos();
            if (odontologos.isEmpty()) {
                logger.error("No se encontraron odontólogos");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron odontólogos");
            }
            return ResponseEntity.ok(odontologos);
        } catch (Exception e) {
            logger.error("Error al buscar todos los odontólogos", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener los odontólogos");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> guardar(@RequestBody Odontologo odontologo) {
        try{
            Odontologo odontologoGuardado = odontologoService.guardar(odontologo);
            logger.info("Odontólogo con matrícula: " + odontologoGuardado.getMatricula() + " guardado correctamente");
            return ResponseEntity.status(HttpStatus.OK).body("Se ha guardado el odontólogo: "+ odontologoGuardado);
        }catch(Exception e){
            logger.error("Error al guardar el Odontólogo: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el odontólogo");
        }
    }

    @GetMapping("{matricula}")
    public ResponseEntity<?> getOdontologoByMatricula(@PathVariable String matricula) throws Exception {
        try {
            Odontologo odontologoEncontrado = odontologoService.buscar(matricula);
            return ResponseEntity.ok(odontologoEncontrado);
        } catch (Exception e){
            if (e.getMessage() != null) {
                logger.error("Error al buscar el Odontólogo con la matrícula: " + matricula + e);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el Odontólogo con matrícula: " + matricula);
            } else {
                logger.error("Error al eliminar el Odontólogo con la matrícula: " + matricula, e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar el Odontólogo");
            }
        }
    }

    @PutMapping("{matricula}")
    public ResponseEntity<?> actualizarOdontologo(@PathVariable String matricula, @RequestBody Odontologo odontologo) throws Exception{
        try {
            Odontologo odontologoExistente = odontologoService.buscar(matricula);
            if(odontologoExistente != null) {
                odontologo.setMatricula(matricula);
                Odontologo odontologoActualizado = odontologoService.actualizar(odontologo);
                logger.info("Odontólogo con matrícula: " + odontologoActualizado.getMatricula() + " actualizado correctamente");
                return ResponseEntity.status(HttpStatus.OK).body("Se ha actualizado el odontólogo: "+ odontologoActualizado);
            } else {
                logger.error("No se encontró el Odontólogo con la matrícula: " + matricula);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al obtener el odontólogo");
            }
        } catch (Exception e){
            logger.error("Error al actualizar el Odontólogo: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el odontólogo");
        }
    }

    @DeleteMapping("{matricula}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable String matricula) throws Exception{
        try{
            odontologoService.eliminar(matricula);
            logger.info("Odontólogo con matrícula " + matricula + " eliminado correctamente");
            return ResponseEntity.ok("Odontólogo con matrícula " + matricula + " eliminado correctamente");
        } catch (Exception e) {
            if (e.getMessage() != null) {
                logger.error("Error al eliminar el Odontólogo con la matrícula: " + matricula + e);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el Odontólogo con matrícula: " + matricula);
            } else {
                logger.error("Error al eliminar el Odontólogo con la matrícula: " + matricula, e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el Odontólogo");
            }
        }
    }
}
