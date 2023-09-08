package com.integrador.ReservaCitas.service.impl;

import com.integrador.ReservaCitas.entity.Odontologo;
import com.integrador.ReservaCitas.entity.Paciente;
import com.integrador.ReservaCitas.entity.Turno;
import com.integrador.ReservaCitas.repository.TurnoRepository;
import com.integrador.ReservaCitas.service.IService;
import com.integrador.ReservaCitas.util.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TurnoService implements IService<Turno> {

    private final TurnoRepository turnoRepository;
    private final OdontologoService odontologoService;
    private final PacienteService pacienteService;

    @Override
    public Turno guardar(Turno turno){
        if(turnoRepository.existsById(turno.getId()))
            throw new DataAccessException("Ya existe un turno con id: " + turno.getId()) {};

        Odontologo odontologo = odontologoService.buscar(turno.getOdontologo().getMatricula());
        Paciente paciente = pacienteService.buscar(turno.getPaciente().getDni());

        turno.setOdontologo(odontologo);
        turno.setPaciente(paciente);

        Date fechaHoraDate = turno.getFecha();
        LocalDateTime localDateTime = fechaHoraDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String horaFormateada = localDateTime.format(formatter);
        turno.setHora(horaFormateada);
        return turnoRepository.save(turno);
    }

    @Override
    public void eliminar(String id){
        int idT = Integer.parseInt(id);
        if(!turnoRepository.existsById(idT))
            throw new ResourceNotFoundException(id, "Turno id");
        turnoRepository.deleteById(idT);
    }

    @Override
    public Turno buscar(String id){
        if(!turnoRepository.existsById(Integer.parseInt(id)))
            throw new ResourceNotFoundException(id, "Turno id");
        return turnoRepository.findById(Integer.parseInt(id)).orElse(null);
    }

    @Override
    public List<Turno> buscarTodos(){
        if(turnoRepository.findAll().isEmpty())
            throw new EmptyResultDataAccessException("No hay turnos cargados", 0);
        return turnoRepository.findAll();
    }

    @Override
    public Turno actualizar(Turno turno){
        if(!turnoRepository.existsById(turno.getId()))
            throw new EmptyResultDataAccessException("No existe un turno con id: " + turno.getId(), 0);
        Odontologo odontologo = odontologoService.buscar(turno.getOdontologo().getMatricula());
        Paciente paciente = pacienteService.buscar(turno.getPaciente().getDni());
        turno.setOdontologo(odontologo);
        turno.setPaciente(paciente);
        return turnoRepository.save(turno);
    }
}
