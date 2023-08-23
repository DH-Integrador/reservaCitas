package com.integrador.ReservaCitas.repository.impl;

import com.integrador.ReservaCitas.ReservaCitasApplication;
import com.integrador.ReservaCitas.model.Odontologo;
import com.integrador.ReservaCitas.service.impl.OdontologoService;
import com.integrador.ReservaCitas.util.SQLConnection;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.sql.Connection;
import java.util.List;

@SpringBootTest
class OdontologoDaoH2Test {

    private static final Logger logger = Logger.getLogger(ReservaCitasApplication.class);
    private static OdontologoDaoH2 odontologoDaoH2;
    @Autowired
    private OdontologoService odontologoService;

    @BeforeEach
    void setUp() {
        try{
            Connection connection = SQLConnection.getConnection();
            SQLConnection.createTables();
            logger.info("Se realizó la creación de tablas en la base de datos");
        } catch (Exception e){
            logger.error("Error al crear las tablas en la base de datos: " + e);
        }
    }

    @Test
    @DirtiesContext
    void testGuardar() throws Exception {
        Odontologo nuevoOdontologo = new Odontologo("123", "Juan", "Pérez");
        Odontologo odontologoGuardado = odontologoService.guardar(nuevoOdontologo);
        Assert.assertNotNull(odontologoGuardado);
        Assert.assertEquals(odontologoGuardado.getMatricula(), "123");
        Assert.assertEquals(nuevoOdontologo, odontologoGuardado);
    }

    @Test
    @DirtiesContext
    void testEliminar() throws Exception {
        Odontologo odontologoEliminar = new Odontologo("123", "Juan", "Pérez");
        odontologoService.guardar(odontologoEliminar);

        try{
            odontologoService.eliminar("123");
        } catch (RuntimeException e){
            Assert.assertEquals("No se pudo encontrar el odontólogo", e.getMessage());
        }
    }

    @Test
    @DirtiesContext
    void testBuscar() throws Exception {
        Odontologo odontologoBuscado = new Odontologo("123", "Juan", "Pérez");
        odontologoService.guardar(odontologoBuscado);
        Odontologo odontologoEncontrado = odontologoService.buscar("123");
        Assert.assertNotNull(odontologoEncontrado);
        Assert.assertEquals(odontologoBuscado, odontologoEncontrado);
    }

    @Test
    @DirtiesContext
    void testBuscarTodos() throws Exception {
        Odontologo odontologo1 = new Odontologo("123", "Juan", "Pérez");
        Odontologo odontologo2 = new Odontologo("456", "Carlos", "Pérez");
        Odontologo odontologo3 = new Odontologo("789", "María", "Rojas");
        odontologoService.guardar(odontologo1);
        odontologoService.guardar(odontologo2);
        odontologoService.guardar(odontologo3);
        List<Odontologo> odontologos = odontologoService.buscarTodos();
        Assert.assertEquals(3, odontologos.size());
        Assert.assertTrue(odontologos.contains(odontologo1));
        Assert.assertTrue(odontologos.contains(odontologo2));
        Assert.assertTrue(odontologos.contains(odontologo3));
    }

    @Test
    @DirtiesContext
    void testActualizar() throws Exception {
        Odontologo odontologoGuardado = new Odontologo("123", "Juan", "Pérez");
        odontologoService.guardar(odontologoGuardado);
        Odontologo odontologoActualizar = new Odontologo("123", "María", "Pérez");
        odontologoService.actualizar(odontologoActualizar);
        Odontologo odontologoActualizado = odontologoService.buscar("123");
        Assert.assertNotNull(odontologoActualizado);
        Assert.assertEquals(odontologoActualizar, odontologoActualizado);
    }
}