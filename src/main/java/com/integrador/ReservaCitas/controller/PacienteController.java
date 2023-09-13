package com.integrador.ReservaCitas.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.integrador.ReservaCitas.dto.GetPacienteDto;
import com.integrador.ReservaCitas.dto.PacienteDto;
import com.integrador.ReservaCitas.entity.Domicilio;
import com.integrador.ReservaCitas.entity.Paciente;
import com.integrador.ReservaCitas.service.IService;
import com.integrador.ReservaCitas.service.impl.PacienteService;
import com.integrador.ReservaCitas.util.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PacienteController {

    private final IService<Paciente> pacienteService;
    private final IService<Domicilio> domicilioService;
    private final ObjectMapper mapper;
    @GetMapping
    public ResponseEntity<List<GetPacienteDto>> listar() {
        log.info("Me llego: listar pacientes  ");
        List<GetPacienteDto> response;
        try{
            List<Paciente> pacientes = pacienteService.buscarTodos();
            response = mapper.convertValue(pacientes, new TypeReference<>() {
            });
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(EmptyResultDataAccessException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/register")
    public ResponseEntity<PacienteDto> guardar(@Validated  @RequestBody PacienteDto paciente) {
        log.info("Me llego: " + paciente);
        Paciente response;
        try{
            response = pacienteService.guardar(mapper.convertValue(paciente, Paciente.class));
            return new ResponseEntity<>(mapper.convertValue(response, PacienteDto.class), HttpStatus.CREATED);
        }catch(DataAccessException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("{Dni}")
    public ResponseEntity<GetPacienteDto> obtenerPorDni(@PathVariable String Dni){
        log.info("Me llego: obtener paciente por Dni  " + Dni);
        GetPacienteDto response;
        try{
            response = mapper.convertValue(pacienteService.buscar(Dni), GetPacienteDto.class);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(EmptyResultDataAccessException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("{Dni}")
    public ResponseEntity<String> actualizar(@PathVariable String Dni, @Validated @RequestBody Paciente paciente){
        log.info("Me llego: actualizar paciente por Dni  " + Dni);
        Paciente response;
        try{
            response = pacienteService.buscar(Dni);
            paciente.setFechaAlta(response.getFechaAlta());
            Domicilio domicilio = paciente.getDomicilio();
            domicilio.setId(response.getDomicilio().getId());
            Domicilio domicilioActualizado = domicilioService.actualizar(domicilio);
            paciente.setDomicilio(domicilioActualizado);
            pacienteService.actualizar(paciente);
            return new ResponseEntity<>("Paciente actualizado correctamente", HttpStatus.OK);
        }catch(EmptyResultDataAccessException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("{Dni}")
    public ResponseEntity<String> eliminar(@PathVariable String Dni){
        log.info("Me llego: eliminar paciente por Dni  " + Dni);
        Paciente response;
        try{
            response = pacienteService.buscar(Dni);
            pacienteService.eliminar(response.getDni());
            return new ResponseEntity<>("Paciente eliminado correctamente", HttpStatus.OK);
        }catch(EmptyResultDataAccessException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}