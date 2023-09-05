package com.integrador.ReservaCitas.controller;

import com.integrador.ReservaCitas.dto.GetPacienteDto;
import com.integrador.ReservaCitas.dto.PacienteDto;
import com.integrador.ReservaCitas.entity.Paciente;
import com.integrador.ReservaCitas.service.IService;
import com.integrador.ReservaCitas.service.impl.PacienteService;
import com.integrador.ReservaCitas.util.Mapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    private final IService<Paciente> pacienteService;
    private static final Logger logger = Logger.getLogger(PacienteController.class);

    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }
    @GetMapping
    public ResponseEntity<Object> listar() {
        try {
           List<Paciente> pacientes = pacienteService.buscarTodos();
           if (pacientes.isEmpty()){
               logger.error("No se encontraron pacientes");
               Map<String, Object> response = new HashMap<>();
               response.put("status","success");
               response.put("message", "No se encontaron pacientes");
               return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
            }
            return ResponseEntity.ok(pacientes);
        } catch (DataAccessException e) {
            logger.error("Error al buscar todos los pacientes", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/register")
    public ResponseEntity<Object> guardar(@Validated  @RequestBody Paciente paciente) {
        try{
            Paciente pacienteGuardado = pacienteService.guardar(paciente);
            logger.info("Paciente con Dni: " + pacienteGuardado.getDni() + " guardado correctamente");
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Se ha guardado el paciente: "+ pacienteGuardado);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch(DataAccessException e){
            logger.error("Error al guardar el Paciente: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el paciente");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("{Dni}")
    public ResponseEntity<Paciente> obtenerPorDni(@PathVariable String Dni) throws DataAccessException {
        try {
            Paciente pacienteEncontrado = pacienteService.buscar(Dni);
            return ResponseEntity.ok(pacienteEncontrado);
        } catch (DataAccessException e){
            logger.error("Error al buscar el Paciente con Dni: " + Dni + e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @PutMapping("{Dni}")
    public ResponseEntity<Object> actualizar(@PathVariable String Dni, @Validated @RequestBody Paciente paciente) throws DataAccessException{
        try {
            Paciente pacienteExistente = pacienteService.buscar(Dni);
            if(pacienteExistente != null) {
                paciente.setDni(Dni);
                Paciente pacienteActualizado = pacienteService.actualizar(paciente);
                logger.info("Paciente con Dni: " + pacienteActualizado.getDni() + " actualizado correctamente");
                Map<String, Object> response = new HashMap<>();
                response.put("status", "success");
                response.put("message", "Se ha actualizado el paciente: "+ pacienteActualizado);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
            } else {
                logger.error("No se encontró el Paciente con Dni: " + Dni);
                return ResponseEntity.notFound().build();
            }
        } catch (DataAccessException e){
            logger.error("Error al actualizar el Paciente: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el paciente");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @DeleteMapping("{Dni}")
    public ResponseEntity<Object> eliminar(@PathVariable String Dni) throws DataAccessException{
        try{
            pacienteService.eliminar(Dni);
            logger.info("Paciente con Dni " + Dni + " eliminado correctamente");
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Se ha eliminado el paciente con Dni: "+ Dni);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (EmptyResultDataAccessException e) {
                logger.error("Error al eliminar el Paciente con Dni: " + Dni + e);
                return ResponseEntity.notFound().build();
        } catch (DataAccessException e) {
                logger.error("Error al eliminar el Paciente con Dni: " + Dni, e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el Paciente");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}