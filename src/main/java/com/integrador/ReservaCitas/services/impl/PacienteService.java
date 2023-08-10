package com.integrador.ReservaCitas.services.impl;

import com.integrador.ReservaCitas.models.Paciente;
import com.integrador.ReservaCitas.services.IService;

import java.sql.SQLException;
import java.util.List;

public class PacienteService implements IService<Paciente> {
    @Override
    public Paciente guardar(Paciente paciente) throws Exception {
        return null;
    }

    @Override
    public void eliminar(String id) throws Exception{

    }

    @Override
    public Paciente buscar(String id) throws Exception {
        return null;
    }

    @Override
    public List<Paciente> buscarTodos() throws SQLException {
        return null;
    }

    @Override
    public Paciente actualizar(Paciente paciente) throws Exception {
        return null;
    }
}
