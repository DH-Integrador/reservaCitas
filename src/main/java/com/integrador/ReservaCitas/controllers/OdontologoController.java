package com.integrador.ReservaCitas.controllers;

import com.integrador.ReservaCitas.models.Odontologo;
import com.integrador.ReservaCitas.services.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
public class OdontologoController {

    private final OdontologoService odontologoService;

    @Autowired
    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @GetMapping("/odontologos")
    public List<Odontologo> getOdontologos() throws SQLException {
        return odontologoService.buscarTodos();
    }

    @PostMapping("/register")
    public Odontologo guardar(Odontologo odontologo) throws Exception {
        return odontologoService.guardarOdontologo(odontologo);
    }
}
