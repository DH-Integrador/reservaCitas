package com.integrador.ReservaCitas.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.integrador.ReservaCitas.dto.DomicilioDto;
import com.integrador.ReservaCitas.dto.GetPacienteDto;
import com.integrador.ReservaCitas.dto.PacienteDto;
import com.integrador.ReservaCitas.entity.Paciente;
import com.integrador.ReservaCitas.entity.Domicilio;

import org.apache.log4j.Logger;

public class Mapper {

    private static final Logger logger = Logger.getLogger(Mapper.class);
    private static ObjectMapper mapper = new ObjectMapper();

    public static Paciente map(PacienteDto dto){
        Paciente paciente = new Paciente();
        Domicilio domicilio = new Domicilio();

        paciente.setDni(dto.getDni());
        paciente.setNombre(dto.getNombre());
        paciente.setApellido(dto.getApellido());

        domicilio.setCalle(dto.getDomicilio().getCalle());
        domicilio.setLocalidad(dto.getDomicilio().getLocalidad());
        domicilio.setNumero(dto.getDomicilio().getNumero());
        domicilio.setProvincia(dto.getDomicilio().getProvincia());

        paciente.setDomicilio(domicilio);
        return paciente;
    }

    public static DomicilioDto map(Domicilio domicilio){
        DomicilioDto dto = mapper.convertValue(domicilio, DomicilioDto.class);
        return dto;
    }

    public static GetPacienteDto map(Paciente paciente){
        GetPacienteDto dto = new GetPacienteDto();
        DomicilioDto domicilio = mapper.convertValue(paciente.getDomicilio(), DomicilioDto.class);

        dto.setDni(paciente.getDni());
        dto.setNombre(paciente.getNombre());
        dto.setApellido(paciente.getApellido());
        dto.setFechaAlta(paciente.getFechaAlta());
        dto.setDomicilio(domicilio);
        return dto;
    }
}
