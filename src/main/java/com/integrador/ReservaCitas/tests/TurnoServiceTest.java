package com.integrador.ReservaCitas.tests;

import com.integrador.ReservaCitas.entity.Odontologo;
import com.integrador.ReservaCitas.entity.Paciente;
import com.integrador.ReservaCitas.entity.Turno;
import com.integrador.ReservaCitas.repository.OdontologoRepository;
import com.integrador.ReservaCitas.repository.PacienteRespository;
import com.integrador.ReservaCitas.repository.TurnoRepository;
import com.integrador.ReservaCitas.service.IService;
import com.integrador.ReservaCitas.service.impl.OdontologoService;
import com.integrador.ReservaCitas.service.impl.PacienteService;
import com.integrador.ReservaCitas.service.impl.TurnoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@SpringJUnitConfig
@ActiveProfiles("test")
class TurnoServiceTest {

    @Autowired
    private TurnoRepository turnoRepository;
    private OdontologoService odontologoService;
    @Autowired
    private OdontologoRepository odontologoRepository;
    private PacienteService pacienteService;
    @Autowired
    private PacienteRespository pacienteRespository;
    private IService<Turno> turnoService;

    @BeforeEach
    void setUp() {
        odontologoService = new OdontologoService(odontologoRepository);
        pacienteService = new PacienteService(pacienteRespository);
        turnoService = new TurnoService(turnoRepository, odontologoService, pacienteService);
    }

    @Test
    void guardar() {
        Turno turno = new Turno();

        Odontologo odontologo = new Odontologo();
        odontologo.setMatricula("1234");
        Odontologo odontologoGuardado = odontologoService.guardar(odontologo);

        Paciente paciente = new Paciente();
        paciente.setDni("12345678");
        Paciente pacienteGuardado = pacienteService.guardar(paciente);

        turno.setOdontologo(odontologoGuardado);
        turno.setPaciente(pacienteGuardado);
        turno.setFecha(new Date());

        Turno turnoGuardado = turnoService.guardar(turno);

        assertNotNull(turnoGuardado);
        assertEquals(turno.getFecha(), turnoGuardado.getFecha());
    }

    @Test
    void eliminar() {
        Turno turno = new Turno();

        Odontologo odontologo = new Odontologo();
        odontologo.setMatricula("1234");
        Odontologo odontologoGuardado = odontologoService.guardar(odontologo);

        Paciente paciente = new Paciente();
        paciente.setDni("12345678");
        Paciente pacienteGuardado = pacienteService.guardar(paciente);

        turno.setOdontologo(odontologoGuardado);
        turno.setPaciente(pacienteGuardado);
        turno.setFecha(new Date());

        Turno turnoGuardado = turnoService.guardar(turno);

        String id = turnoGuardado.getId() + "";
        turnoService.eliminar(id);
        assertThrows(RuntimeException.class, () -> turnoService.buscar(id));
    }

    @Test
    void buscar() {
        Turno turno = new Turno();

        Odontologo odontologo = new Odontologo();
        odontologo.setMatricula("1234");
        Odontologo odontologoGuardado = odontologoService.guardar(odontologo);

        Paciente paciente = new Paciente();
        paciente.setDni("12345678");
        Paciente pacienteGuardado = pacienteService.guardar(paciente);

        turno.setOdontologo(odontologoGuardado);
        turno.setPaciente(pacienteGuardado);
        turno.setFecha(new Date());

        Turno turnoGuardado = turnoService.guardar(turno);

        String id = turnoGuardado.getId() + "";
        Turno turnoBuscado = turnoService.buscar(id);
        assertNotNull(turnoBuscado);
        assertEquals(turno.getFecha(), turnoBuscado.getFecha());
    }

    @Test
    void buscarTodos() {
        Turno turno = new Turno();

        Odontologo odontologo = new Odontologo();
        odontologo.setMatricula("1234");
        Odontologo odontologoGuardado = odontologoService.guardar(odontologo);

        Paciente paciente = new Paciente();
        paciente.setDni("12345678");
        Paciente pacienteGuardado = pacienteService.guardar(paciente);

        turno.setOdontologo(odontologoGuardado);
        turno.setPaciente(pacienteGuardado);
        turno.setFecha(new Date());

        Turno turnoGuardado = turnoService.guardar(turno);

        Turno turno2 = new Turno();

        Odontologo odontologo2 = new Odontologo();
        odontologo2.setMatricula("5678");
        Odontologo odontologoGuardado2 = odontologoService.guardar(odontologo2);

        Paciente paciente2 = new Paciente();
        paciente2.setDni("87654321");
        Paciente pacienteGuardado2 = pacienteService.guardar(paciente2);

        turno2.setOdontologo(odontologoGuardado2);
        turno2.setPaciente(pacienteGuardado2);
        turno2.setFecha(new Date());

        Turno turnoGuardado2 = turnoService.guardar(turno2);

        assertEquals(2, turnoService.buscarTodos().size());
    }

    @Test
    void actualizar() {
        Turno turno = new Turno();

        Odontologo odontologo = new Odontologo();
        odontologo.setMatricula("1234");
        Odontologo odontologoGuardado = odontologoService.guardar(odontologo);

        Paciente paciente = new Paciente();
        paciente.setDni("12345678");
        Paciente pacienteGuardado = pacienteService.guardar(paciente);

        turno.setOdontologo(odontologoGuardado);
        turno.setPaciente(pacienteGuardado);
        turno.setFecha(new Date());

        Turno turnoGuardado = turnoService.guardar(turno);

        turno.setFecha(new Date());
        turnoService.actualizar(turno);
        Turno turnoActualizado = turnoService.buscar(turnoGuardado.getId() + "");
        assertEquals(turno.getFecha(), turnoActualizado.getFecha());
    }
}