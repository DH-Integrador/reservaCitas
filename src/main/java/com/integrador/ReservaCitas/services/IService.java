package com.integrador.ReservaCitas.services;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {
    public abstract T guardar(T t) throws Exception;
    public abstract void eliminar(String id) throws Exception;
    public abstract T actualizar(T t) throws Exception;
    public abstract T buscar(String id) throws Exception;
    public abstract List<T> buscarTodos() throws SQLException;
}
