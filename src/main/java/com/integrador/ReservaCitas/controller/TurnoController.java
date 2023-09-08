package com.integrador.ReservaCitas.controller;

import com.integrador.ReservaCitas.entity.Turno;
import com.integrador.ReservaCitas.service.IService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("turno")
@Log4j2
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TurnoController {
    private final IService<Turno> turnoService;
    @GetMapping
    public ResponseEntity<List<Turno>> listar(){
        log.info("Me llego: listar turnos");
        List<Turno> response;
        try{
            List<Turno> turnos = turnoService.buscarTodos();
            response = turnos;
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (DataAccessException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Turno> guardar(@Validated @RequestBody Turno turno){
        log.info("Me llego: " + turno);
        Turno response;
        try{
            response = turnoService.guardar(turno);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (DataAccessException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turno> buscarPorId(@PathVariable Integer id){
        log.info("Me llego: obtener turno por id  " + id);
        Turno response;
        try{
            response = turnoService.buscar(String.valueOf(id));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (DataAccessException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Turno> actualizar(@PathVariable Integer id, @Validated @RequestBody Turno turno){
        log.info("Me llego: " + turno);
        Turno response;
        try{
            response = turnoService.actualizar(turno);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (DataAccessException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Turno> eliminar(@PathVariable Integer id){
        log.info("Me llego: eliminar turno por id  " + id);
        Turno response;
        try{
            response = turnoService.buscar(String.valueOf(id));
            String idTurno = String.valueOf(response.getId());
            turnoService.eliminar(idTurno);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (DataAccessException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
