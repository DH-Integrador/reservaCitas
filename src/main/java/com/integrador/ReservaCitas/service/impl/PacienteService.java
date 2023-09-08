package com.integrador.ReservaCitas.service.impl;

import com.integrador.ReservaCitas.entity.Paciente;
import com.integrador.ReservaCitas.repository.PacienteRespository;
import com.integrador.ReservaCitas.service.IService;
import com.integrador.ReservaCitas.util.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PacienteService implements IService<Paciente> {
    private final PacienteRespository pacienteRespository;
    @Override
    public Paciente guardar(Paciente paciente){
        if(pacienteRespository.existsById(paciente.getDni()))
            throw new DataIntegrityViolationException("Ya existe un paciente con dni: " + paciente.getDni()) {};
        Date fechaAlta = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String fechaAltaString = formatter.format(fechaAlta);
        paciente.setFechaAlta(fechaAltaString);
        return pacienteRespository.save(paciente);
    }

    @Override
    public void eliminar(String dni){
        if(!pacienteRespository.existsById(dni))
            throw new ResourceNotFoundException(dni, "Paciente dni");
        pacienteRespository.deleteById(dni);
    }

    @Override
    public Paciente buscar(String dni){
        if(!pacienteRespository.existsById(dni))
            throw new ResourceNotFoundException(dni, "Paciente dni") {};
        return pacienteRespository.findById(dni).orElse(null);
    }

    @Override
    public List<Paciente> buscarTodos(){
        if(pacienteRespository.findAll().isEmpty())
            throw new EmptyResultDataAccessException("No hay pacientes cargados", 0);
        return pacienteRespository.findAll();
    }

    @Override
    public Paciente actualizar(Paciente paciente){
        if(!pacienteRespository.existsById(paciente.getDni()))
            throw new ResourceNotFoundException(paciente.getDni(), "Paciente dni") {};
        return pacienteRespository.save(paciente);
    }
}
