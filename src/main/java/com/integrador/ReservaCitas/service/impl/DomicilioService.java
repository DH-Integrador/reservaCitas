package com.integrador.ReservaCitas.service.impl;

import com.integrador.ReservaCitas.entity.Domicilio;
import com.integrador.ReservaCitas.repository.DomicilioRepository;
import com.integrador.ReservaCitas.service.IService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class DomicilioService implements IService<Domicilio> {

    private final DomicilioRepository domicilioRepository;

    @Override
    public Domicilio guardar(Domicilio domicilio){
        if(domicilioRepository.existsById(domicilio.getId()))
            throw new DataAccessException("Ya existe un domicilio con id: " + domicilio.getId()) {};
        return domicilioRepository.save(domicilio);
    }

    @Override
    public void eliminar(String id){

    }

    @Override
    public Domicilio actualizar(Domicilio domicilio){
        return null;
    }

    @Override
    public Domicilio buscar(String id){
        int idInt = Integer.parseInt(id);
        if(!domicilioRepository.existsById(idInt))
            throw new DataAccessException("No existe un domicilio con id: " + id) {};
        return domicilioRepository.findById(idInt).orElse(null);
    }

    @Override
    public List<Domicilio> buscarTodos(){
        return null;
    }
}
