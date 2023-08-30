package com.integrador.ReservaCitas.controller;

import com.integrador.ReservaCitas.entity.Odontologo;
import com.integrador.ReservaCitas.entity.Turno;
import com.integrador.ReservaCitas.service.IService;
import com.integrador.ReservaCitas.service.impl.OdontologoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("odontologos")
public class OdontologoController {

    private final IService<Odontologo> odontologoService;
    private static final Logger logger = Logger.getLogger(OdontologoController.class);

    @Autowired
    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> listar() {
        try {
            List<Odontologo> odontologos = odontologoService.buscarTodos();
            if (odontologos.isEmpty()) {
                logger.error("No se encontraron odontólogos");
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(odontologos);
        } catch (DataAccessException e) {
            logger.error("Error al buscar todos los odontólogos", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> guardar(@Validated @RequestBody Odontologo odontologo) {
        try{
            Odontologo odontologoGuardado = odontologoService.guardar(odontologo);
            logger.info("Odontólogo con matrícula: " + odontologoGuardado.getMatricula() + " guardado correctamente");
            return ResponseEntity.ok("Se ha guardado el odontólogo: "+ odontologoGuardado);
        }catch(DataAccessException e){
            logger.error("Error al guardar el Odontólogo: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el odontólogo");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("{matricula}")
    public ResponseEntity<Odontologo> obtenerPorMatricula(@PathVariable String matricula) throws DataAccessException {
        try {
            Odontologo odontologoEncontrado = odontologoService.buscar(matricula);
            return ResponseEntity.ok(odontologoEncontrado);
        } catch (DataAccessException e){
            logger.error("Error al buscar el Odontólogo con matrícula: " + matricula, e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("{matricula}")
    public ResponseEntity<String> actualizar(@PathVariable String matricula, @Validated @RequestBody Odontologo odontologo) throws DataAccessException{
        try {
            Odontologo odontologoExistente = odontologoService.buscar(matricula);
            if(odontologoExistente != null) {
                odontologo.setMatricula(matricula);
                Odontologo odontologoActualizado = odontologoService.actualizar(odontologo);
                logger.info("Odontólogo con matrícula: " + odontologoActualizado.getMatricula() + " actualizado correctamente");
                return ResponseEntity.ok("Se ha actualizado el odontólogo: "+ odontologoActualizado);
            } else {
                logger.error("No se encontró el Odontólogo con la matrícula: " + matricula);
                return ResponseEntity.notFound().build();
            }
        } catch (DataAccessException e){
            logger.error("Error al actualizar el Odontólogo: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el odontólogo");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("{matricula}")
    public ResponseEntity<String> eliminar(@PathVariable String matricula) throws DataAccessException{
        try{
            odontologoService.eliminar(matricula);
            logger.info("Odontólogo con matrícula " + matricula + " eliminado correctamente");
            return ResponseEntity.ok("Odontólogo con matrícula " + matricula + " eliminado correctamente");
        } catch (EmptyResultDataAccessException e) {
            logger.error("No se encontró el Odontólogo con matrícula: " + matricula, e);
            return ResponseEntity.notFound().build();
        } catch (DataAccessException e){
            logger.error("Error al eliminar el Odontólogo con la matrícula: " + matricula, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el odontólogo");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
