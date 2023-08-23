package com.integrador.ReservaCitas.controller;

import com.integrador.ReservaCitas.dto.PacienteDto;
import com.integrador.ReservaCitas.model.Paciente;
import com.integrador.ReservaCitas.service.impl.PacienteService;
import com.integrador.ReservaCitas.util.Mapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    private final PacienteService pacienteService;
    private static final Logger logger = Logger.getLogger(PacienteController.class);

    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }
    @GetMapping
    public ResponseEntity<?> getPacientes() {
        try {
            List<Paciente> pacientes = pacienteService.buscarTodos();
            if (pacientes.isEmpty()) {
                logger.error("No se encontraron pacientes");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron pacientes");
            }
            return ResponseEntity.ok(pacientes);
        } catch (Exception e) {
            logger.error("Error al buscar todos los pacientes", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener los pacientes");
        }
    }
    @PostMapping("/register")
    public ResponseEntity<?> guardar(@RequestBody PacienteDto paciente) {
        try{
            logger.info("Paciente recibido: " + paciente);
            logger.info(Mapper.map(paciente));
            Paciente pacienteGuardado = pacienteService.guardar(Mapper.map(paciente));
            logger.info("Paciente con Dni: " + pacienteGuardado.getDni() + " guardado correctamente");
            return ResponseEntity.status(HttpStatus.OK).body("Se ha guardado el paciente: "+ pacienteGuardado);
        }catch(Exception e){
            logger.error("Error al guardar el Paciente: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el paciente");
        }
    }
    @GetMapping("{Dni}")
    public ResponseEntity<?> getPacienteByDni(@PathVariable String Dni) throws Exception {
        try {
            Paciente pacienteEncontrado = pacienteService.buscar(Dni);
            return ResponseEntity.ok(pacienteEncontrado);
        } catch (Exception e){
            if (e.getMessage() != null) {
                logger.error("Error al buscar el Paciente con Dni: " + Dni + e);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el Paciente con Dni: " + Dni);
            } else {
                logger.error("Error al eliminar el Paciente con Dni: " + Dni, e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar el Paciente");
            }
        }
    }
    @PutMapping("{Dni}")
    public ResponseEntity<?> actualizarPaciente(@PathVariable String Dni, @RequestBody Paciente paciente) throws Exception{
        try {
            Paciente pacienteExistente = pacienteService.buscar(Dni);
            if(pacienteExistente != null) {
                paciente.setDni(Dni);
                Paciente pacienteActualizado = pacienteService.actualizar(paciente);
                logger.info("Paciente con Dni: " + pacienteActualizado.getDni() + " actualizado correctamente");
                return ResponseEntity.status(HttpStatus.OK).body("Se ha actualizado el paciente: "+ pacienteActualizado);
            } else {
                logger.error("No se encontró el Paciente con Dni: " + Dni);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al obtener el paciente");
            }
        } catch (Exception e){
            logger.error("Error al actualizar el Paciente: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el paciente");
        }
    }@DeleteMapping("{Dni}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable String Dni) throws Exception{
        try{
            pacienteService.eliminar(Dni);
            logger.info("Paciente con Dni " + Dni + " eliminado correctamente");
            return ResponseEntity.ok("Paciente con Dni " + Dni + " eliminado correctamente");
        } catch (Exception e) {
            if (e.getMessage() != null) {
                logger.error("Error al eliminar el Paciente con Dni: " + Dni + e);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el Paciente con Dni: " + Dni);
            } else {
                logger.error("Error al eliminar el Paciente con Dni: " + Dni, e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el Paciente");
            }
        }
    }
}