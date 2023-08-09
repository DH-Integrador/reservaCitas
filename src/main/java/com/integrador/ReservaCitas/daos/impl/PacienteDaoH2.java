package com.integrador.ReservaCitas.daos.impl;

import com.integrador.ReservaCitas.daos.IDao;
import com.integrador.ReservaCitas.models.Paciente;

import java.sql.SQLException;
import java.util.List;

public class PacienteDaoH2 implements IDao<Paciente> {
    @Override
    public Paciente guardar(Paciente paciente) throws Exception {
        return null;
    }

    @Override
    public void eliminar(String matricula) {

    }

    @Override
    public Paciente buscar(String matricula) throws Exception {
        return null;
    }

    @Override
    public List<Paciente> buscarTodos() throws SQLException {
        return null;
    }
}
