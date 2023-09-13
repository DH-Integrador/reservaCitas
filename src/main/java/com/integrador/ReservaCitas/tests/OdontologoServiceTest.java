package com.integrador.ReservaCitas.tests;

import com.integrador.ReservaCitas.entity.Odontologo;
import com.integrador.ReservaCitas.repository.OdontologoRepository;
import com.integrador.ReservaCitas.service.IService;
import com.integrador.ReservaCitas.service.impl.OdontologoService;
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
class OdontologoServiceTest {

    @Autowired
    private OdontologoRepository odontologoRepository;
    private IService<Odontologo> odontologoService;

    @BeforeEach
    void setUp() {
        odontologoService = new OdontologoService(odontologoRepository);
    }

    @Test
    void guardar() {
        Odontologo odontologo = new Odontologo();
        odontologo.setMatricula("1234");
        Odontologo odontologoGuardado = odontologoService.guardar(odontologo);

        assertNotNull(odontologoGuardado);
        assertEquals(odontologo.getMatricula(), odontologoGuardado.getMatricula());
    }

    @Test
    void eliminar() {
        Odontologo odontologo = new Odontologo();
        odontologo.setMatricula("1234");
        odontologoService.guardar(odontologo);
        odontologoService.eliminar(odontologo.getMatricula());
        assertThrows(RuntimeException.class, () -> odontologoService.buscar(odontologo.getMatricula()));
    }

    @Test
    void buscar() {
        Odontologo odontologo = new Odontologo();
        odontologo.setMatricula("1234");
        odontologoService.guardar(odontologo);
        Odontologo odontologoBuscado = odontologoService.buscar(odontologo.getMatricula());
        assertNotNull(odontologoBuscado);
        assertEquals(odontologo.getMatricula(), odontologoBuscado.getMatricula());
    }

    @Test
    void buscarTodos() {
        Odontologo odontologo = new Odontologo();
        odontologo.setMatricula("1234");
        odontologoService.guardar(odontologo);
        Odontologo odontologo2 = new Odontologo();
        odontologo2.setMatricula("5678");
        odontologoService.guardar(odontologo2);
        assertEquals(2, odontologoService.buscarTodos().size());
    }

    @Test
    void actualizar() {
        Odontologo odontologo = new Odontologo();
        odontologo.setMatricula("1234");
        odontologoService.guardar(odontologo);
        odontologo.setNombre("Nuevo nombre");
        odontologoService.actualizar(odontologo);
        Odontologo odontologoActualizado = odontologoService.buscar(odontologo.getMatricula());
        assertEquals(odontologo.getMatricula(), odontologoActualizado.getMatricula());
    }
}