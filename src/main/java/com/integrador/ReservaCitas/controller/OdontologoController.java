package com.integrador.ReservaCitas.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.integrador.ReservaCitas.entity.Odontologo;
import com.integrador.ReservaCitas.entity.Turno;
import com.integrador.ReservaCitas.service.IService;
import com.integrador.ReservaCitas.service.impl.OdontologoService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("odontologos")
@Log4j2
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class OdontologoController {

    private final IService<Odontologo> odontologoService;
    private final ObjectMapper mapper;

    @GetMapping
    public ResponseEntity<List<Odontologo>> listar() {
        log.info("Me llego: listar odontologos  ");
        List<Odontologo> response;
        try{
            List<Odontologo> odontologos = odontologoService.buscarTodos();
            response = mapper.convertValue(odontologos, new TypeReference<>() {
            });
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(EmptyResultDataAccessException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Odontologo> guardar(@Validated @RequestBody Odontologo odontologo) {
       log.info("Me llego: " + odontologo);
       Odontologo response;
       try{
           response = odontologoService.guardar(odontologo);
           return new ResponseEntity<>(response, HttpStatus.CREATED);
       }catch (DataAccessException e){
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }

    @GetMapping("{matricula}")
    public ResponseEntity<Odontologo> obtenerPorMatricula(@PathVariable String matricula) {
        log.info("Me llego: obtener odontologo por matricula  " + matricula);
        Odontologo response;
        try{
            response = odontologoService.buscar(matricula);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (EmptyResultDataAccessException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{matricula}")
    public ResponseEntity<Odontologo> actualizar(@PathVariable String matricula, @Validated @RequestBody Odontologo odontologo){
        log.info("Me llego: " + odontologo);
        Odontologo response;
        try{
            response = odontologoService.actualizar(odontologo);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (DataAccessException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{matricula}")
    public ResponseEntity<Odontologo> eliminar(@PathVariable String matricula){
        log.info("Me llego: eliminar odontologo por matricula  " + matricula);
        Odontologo response;
        try{
            response = odontologoService.buscar(matricula);
            odontologoService.eliminar(response.getMatricula());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (EmptyResultDataAccessException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
