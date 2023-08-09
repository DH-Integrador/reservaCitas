package com.integrador.ReservaCitas.daos;

import java.sql.SQLException;
import java.util.List;

public interface IDao<T>{
    public abstract T guardar(T t) throws Exception;
    public abstract void eliminar(String matricula);
    public abstract T buscar(String matricula) throws Exception;
    public abstract List<T> buscarTodos() throws SQLException;
}
