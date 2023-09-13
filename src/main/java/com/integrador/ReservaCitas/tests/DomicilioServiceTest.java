package com.integrador.ReservaCitas.tests;

import com.integrador.ReservaCitas.entity.Domicilio;
import com.integrador.ReservaCitas.repository.DomicilioRepository;
import com.integrador.ReservaCitas.service.IService;
import com.integrador.ReservaCitas.service.impl.DomicilioService;
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
class DomicilioServiceTest {

    @Autowired
    private DomicilioRepository domicilioRepository;
    private IService<Domicilio> domicilioService;

    @BeforeEach
    void setUp() {
        domicilioService = new DomicilioService(domicilioRepository);
    }

    @Test
    void guardar() {
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle("Calle 123");
        domicilio.setNumero("123");
        domicilio.setLocalidad("Localidad 123");
        domicilio.setProvincia("Provincia 123");
        Domicilio domicilioGuardado = domicilioService.guardar(domicilio);

        assertNotNull(domicilioGuardado);
        assertEquals(domicilio.getCalle(), domicilioGuardado.getCalle());
    }

    @Test
    void eliminar() {
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle("Calle 123");
        domicilio.setNumero("123");
        domicilio.setLocalidad("Localidad 123");
        domicilio.setProvincia("Provincia 123");
        domicilioService.guardar(domicilio);
        String id = domicilio.getId() + "";
        domicilioService.eliminar(id);
        assertThrows(RuntimeException.class, () -> domicilioService.buscar(id));
    }

    @Test
    void actualizar() {
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle("Calle 123");
        domicilio.setNumero("123");
        domicilio.setLocalidad("Localidad 123");
        domicilio.setProvincia("Provincia 123");
        domicilioService.guardar(domicilio);
        domicilio.setCalle("Calle 456");
        domicilioService.actualizar(domicilio);
        Domicilio domicilioBuscado = domicilioService.buscar(domicilio.getId() + "");
        assertEquals(domicilio.getCalle(), domicilioBuscado.getCalle());
    }

    @Test
    void buscar() {
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle("Calle 123");
        domicilio.setNumero("123");
        domicilio.setLocalidad("Localidad 123");
        domicilio.setProvincia("Provincia 123");
        domicilioService.guardar(domicilio);
        Domicilio domicilioBuscado = domicilioService.buscar(domicilio.getId() + "");
        assertNotNull(domicilioBuscado);
        assertEquals(domicilio.getCalle(), domicilioBuscado.getCalle());
    }

    @Test
    void buscarTodos() {
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle("Calle 123");
        domicilio.setNumero("123");
        domicilio.setLocalidad("Localidad 123");
        domicilio.setProvincia("Provincia 123");
        domicilioService.guardar(domicilio);
        Domicilio domicilio2 = new Domicilio();
        domicilio2.setCalle("Calle 456");
        domicilio2.setNumero("456");
        domicilio2.setLocalidad("Localidad 456");
        domicilio2.setProvincia("Provincia 456");
        domicilioService.guardar(domicilio2);
        assertEquals(2, domicilioService.buscarTodos().size());
    }
}