package com.integrador.ReservaCitas.service.impl;

import com.integrador.ReservaCitas.entity.Domicilio;
import com.integrador.ReservaCitas.repository.DomicilioRepository;
import com.integrador.ReservaCitas.service.IService;
import com.integrador.ReservaCitas.util.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        int idInt = Integer.parseInt(id);
        if(!domicilioRepository.existsById(idInt))
            throw new DataAccessException("No existe un domicilio con id: " + id) {};
        domicilioRepository.deleteById(idInt);
    }

    @Override
    public Domicilio actualizar(Domicilio domicilio){
        Optional<Domicilio> existingDomicilio = domicilioRepository.findById(domicilio.getId());
        if(existingDomicilio.isPresent()){
            Domicilio updatedDomicilio = existingDomicilio.get();
            updatedDomicilio.setCalle(domicilio.getCalle());
            updatedDomicilio.setNumero(domicilio.getNumero());
            updatedDomicilio.setLocalidad(domicilio.getLocalidad());
            updatedDomicilio.setProvincia(domicilio.getProvincia());
            return domicilioRepository.save(updatedDomicilio);
        }else{
            throw new DataAccessException("No existe un domicilio con ID: " + domicilio.getId()) {};
        }
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
        if(domicilioRepository.findAll().isEmpty())
            throw new DataAccessException("No existen domicilios") {};
        return domicilioRepository.findAll();
    }
}
