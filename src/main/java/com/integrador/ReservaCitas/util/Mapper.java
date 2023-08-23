package com.integrador.ReservaCitas.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.integrador.ReservaCitas.dto.PacienteDto;
import com.integrador.ReservaCitas.dto.PacienteDtoGet;
import com.integrador.ReservaCitas.model.Domicilio;
import com.integrador.ReservaCitas.model.Paciente;

import org.apache.log4j.Logger;

public class Mapper {

    private static final Logger logger = Logger.getLogger(Mapper.class);
    private static ObjectMapper mapper = new ObjectMapper();

    public static Paciente map(PacienteDto dto){
        Paciente paciente = new Paciente();
        Domicilio domicilio = mapper.convertValue(dto.getDomicilio(), Domicilio.class);

        paciente.setDni(dto.getDni());
        paciente.setNombre(dto.getNombre());
        paciente.setApellido(dto.getApellido());
        paciente.setDomicilio(domicilio);
        return paciente;
    }
}
