package com.integrador.ReservaCitas.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.List;

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
    public ResponseEntity<List<GetPacienteDto>> listar() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<GetPacienteDto> pacientesDto = null;
            pacientesDto = mapper.convertValue(pacienteService.buscarTodos(), new TypeReference<>() {});

            if (pacientesDto.isEmpty()) {
                logger.error("No se encontraron pacientes");
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(pacientesDto);
        } catch (DataAccessException e) {
            logger.error("Error al buscar todos los pacientes", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/register")
    public ResponseEntity<String> guardar(@Validated  @RequestBody PacienteDto paciente) {
        try{
            Paciente pacienteGuardado = pacienteService.guardar(Mapper.map(paciente));
            logger.info("Paciente con Dni: " + pacienteGuardado.getDni() + " guardado correctamente");
            return ResponseEntity.ok("Se ha guardado el paciente: "+ pacienteGuardado);
        }catch(DataAccessException e){
            logger.error("Error al guardar el Paciente: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el paciente");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("{Dni}")
    public ResponseEntity<GetPacienteDto> obtenerPorDni(@PathVariable String Dni) throws DataAccessException {
        try {
            Paciente pacienteEncontrado = pacienteService.buscar(Dni);
            GetPacienteDto pacienteDto = Mapper.map(pacienteEncontrado);
            return ResponseEntity.ok(pacienteDto);
        } catch (DataAccessException e){
            logger.error("Error al buscar el Paciente con Dni: " + Dni + e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @PutMapping("{Dni}")
    public ResponseEntity<String> actualizar(@PathVariable String Dni, @Validated @RequestBody Paciente paciente) throws DataAccessException{
        try {
            Paciente pacienteExistente = pacienteService.buscar(Dni);
            if(pacienteExistente != null) {
                paciente.setDni(Dni);
                Paciente pacienteActualizado = pacienteService.actualizar(paciente);
                logger.info("Paciente con Dni: " + pacienteActualizado.getDni() + " actualizado correctamente");
                return ResponseEntity.ok("Se ha actualizado el paciente: "+ pacienteActualizado);
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
    public ResponseEntity<String> eliminar(@PathVariable String Dni) throws DataAccessException{
        try{
            pacienteService.eliminar(Dni);
            logger.info("Paciente con Dni " + Dni + " eliminado correctamente");
            return ResponseEntity.ok("Paciente con Dni " + Dni + " eliminado correctamente");
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