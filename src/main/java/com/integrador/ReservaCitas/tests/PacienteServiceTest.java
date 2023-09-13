package com.integrador.ReservaCitas.tests;

import com.integrador.ReservaCitas.entity.Paciente;
import com.integrador.ReservaCitas.repository.PacienteRespository;
import com.integrador.ReservaCitas.service.IService;
import com.integrador.ReservaCitas.service.impl.PacienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@SpringJUnitConfig
@ActiveProfiles("test")
class PacienteServiceTest {

    @Autowired
    private PacienteRespository pacienteRespository;
    private IService<Paciente> pacienteIService;

    @BeforeEach
    void setUp() {
        pacienteIService = new PacienteService(pacienteRespository);
    }

    @Test
    void guardar() {
        Paciente paciente = new Paciente();
        paciente.setDni("1234");
        Paciente pacienteGuardado = pacienteIService.guardar(paciente);

        assertNotNull(pacienteGuardado);
        assertEquals(paciente.getDni(), pacienteGuardado.getDni());
    }

    @Test
    void eliminar() {
        Paciente paciente = new Paciente();
        paciente.setDni("1234");
        pacienteIService.guardar(paciente);
        pacienteIService.eliminar(paciente.getDni());
        assertThrows(RuntimeException.class, () -> pacienteIService.buscar(paciente.getDni()));
    }

    @Test
    void buscar() {
        Paciente paciente = new Paciente();
        paciente.setDni("1234");
        pacienteIService.guardar(paciente);
        Paciente pacienteBuscado = pacienteIService.buscar(paciente.getDni());
        assertNotNull(pacienteBuscado);
        assertEquals(paciente.getDni(), pacienteBuscado.getDni());
    }

    @Test
    void buscarTodos() {
        Paciente paciente = new Paciente();
        paciente.setDni("1234");
        pacienteIService.guardar(paciente);
        Paciente pacienteBuscado = pacienteIService.buscar(paciente.getDni());
        assertNotNull(pacienteBuscado);
        assertEquals(paciente.getDni(), pacienteBuscado.getDni());
    }

    @Test
    void actualizar() {
        Paciente paciente = new Paciente();
        paciente.setDni("1234");
        pacienteIService.guardar(paciente);
        paciente.setNombre("Juan");
        pacienteIService.actualizar(paciente);
        assertEquals("Juan", paciente.getNombre());
    }
}