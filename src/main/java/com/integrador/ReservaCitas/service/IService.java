package com.integrador.ReservaCitas.service;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {
    public abstract T guardar(T t);
    public abstract void eliminar(String id);
    public abstract T actualizar(T t);
    public abstract T buscar(String id);
    public abstract List<T> buscarTodos();
}
