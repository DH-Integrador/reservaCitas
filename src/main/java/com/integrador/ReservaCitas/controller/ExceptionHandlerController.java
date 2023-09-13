package com.integrador.ReservaCitas.controller;

import com.integrador.ReservaCitas.dto.ErrorDTO;
import com.integrador.ReservaCitas.util.ResourceNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDTO> recursoNoEncontradoHandler(ResourceNotFoundException exception) {
        String mensaje = "Recuso no encontrado. " + exception.getResource() + ":  " + exception.getResourceId();

        log.error("Se atajo una excepcion. " + mensaje);

        ErrorDTO response = new ErrorDTO("404", mensaje);

        return ResponseEntity.status(404).body(response);
    }
}
