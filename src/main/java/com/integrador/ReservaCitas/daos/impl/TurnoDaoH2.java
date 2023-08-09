package com.integrador.ReservaCitas.daos.impl;

import com.integrador.ReservaCitas.daos.IDao;
import com.integrador.ReservaCitas.models.Turno;

import java.sql.SQLException;
import java.util.List;

public class TurnoDaoH2 implements IDao<Turno> {
    @Override
    public Turno guardar(Turno turno) throws Exception {
        return null;
    }

    @Override
    public void eliminar(String matricula) {

    }

    @Override
    public Turno buscar(String matricula) throws Exception {
        return null;
    }

    @Override
    public List<Turno> buscarTodos() throws SQLException {
        return null;
    }
}
