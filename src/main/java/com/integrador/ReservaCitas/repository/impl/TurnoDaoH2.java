package com.integrador.ReservaCitas.repository.impl;

import com.integrador.ReservaCitas.repository.IDao;
import com.integrador.ReservaCitas.model.Turno;

import java.sql.SQLException;
import java.util.List;

public class TurnoDaoH2 implements IDao<Turno> {
    @Override
    public Turno guardar(Turno turno) throws Exception {
        return null;
    }

    @Override
    public void eliminar(String matricula) throws Exception{

    }

    @Override
    public Turno buscar(String matricula) throws Exception {
        return null;
    }

    @Override
    public List<Turno> buscarTodos() throws SQLException {
        return null;
    }

    @Override
    public Turno actualizar(Turno turno) throws Exception {
        return null;
    }
}
