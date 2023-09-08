package com.integrador.ReservaCitas.service.impl;

import com.integrador.ReservaCitas.entity.Odontologo;
import com.integrador.ReservaCitas.entity.Turno;
import com.integrador.ReservaCitas.repository.OdontologoRepository;
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

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class OdontologoService implements IService<Odontologo>{
    private final OdontologoRepository odontologoRepository;

    @Override
    public Odontologo guardar(Odontologo odontologo) {
        if(odontologoRepository.existsById(odontologo.getMatricula()))
            throw new DataIntegrityViolationException("Ya existe un odontólogo con matrícula: " + odontologo.getMatricula()) {};
        return odontologoRepository.save(odontologo);
    }
    @Override
    public void eliminar(String matricula) {
        if(!odontologoRepository.existsById(matricula))
            throw new ResourceNotFoundException(matricula, "Odontólogo matrícula");
        odontologoRepository.deleteById(matricula);
    }

    @Override
    public Odontologo buscar(String matricula){
        if(!odontologoRepository.existsById(matricula))
            throw new ResourceNotFoundException(matricula, "Odontólogo matrícula");
        return odontologoRepository.findById(matricula).orElse(null);
    }

    @Override
    public List<Odontologo> buscarTodos(){
        if(odontologoRepository.findAll().isEmpty())
            throw new EmptyResultDataAccessException("No hay odontólogos cargados", 0);
        return odontologoRepository.findAll();
    }

    @Override
    public Odontologo actualizar(Odontologo odontologo){
        if(!odontologoRepository.existsById(odontologo.getMatricula()))
            throw new ResourceNotFoundException(odontologo.getMatricula(), "Odontólogo matrícula");
        return odontologoRepository.save(odontologo);
    }
}
