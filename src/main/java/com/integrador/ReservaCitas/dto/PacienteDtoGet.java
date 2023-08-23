package com.integrador.ReservaCitas.dto;

import java.util.Date;

public class PacienteDtoGet {
    private String nombre;
    private String apellido;
    private String dni;
    private Date fechaAlta;
    private DomicilioDto domicilio;

    public PacienteDtoGet() {
    }

    public PacienteDtoGet(String nombre, String apellido, String dni, Date fechaAlta, DomicilioDto domicilio) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaAlta = fechaAlta;
        this.domicilio = domicilio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public DomicilioDto getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(DomicilioDto domicilio) {
        this.domicilio = domicilio;
    }

    @Override
    public String toString() {
        return "PacienteDtoGet{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", fechaAlta=" + fechaAlta +
                ", domicilio=" + domicilio +
                '}';
    }
}
