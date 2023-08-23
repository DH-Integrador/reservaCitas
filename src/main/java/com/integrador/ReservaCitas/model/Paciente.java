package com.integrador.ReservaCitas.model;

import java.util.Date;
import java.util.Objects;

public class Paciente {
    private String dni;
    private String nombre;
    private String apellido;
    private Date fechaAlta;
    private Domicilio domicilio;

    public Paciente() {
    }

    public Paciente(String dni, String nombre, String apellido, Date fechaAlta, Domicilio domicilio) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaAlta = fechaAlta;
        this.domicilio = domicilio;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paciente paciente = (Paciente) o;
        return Objects.equals(dni, paciente.dni) && Objects.equals(nombre, paciente.nombre) && Objects.equals(apellido, paciente.apellido) && Objects.equals(fechaAlta, paciente.fechaAlta) && Objects.equals(domicilio, paciente.domicilio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni, nombre, apellido, fechaAlta, domicilio);
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", fechaAlta=" + fechaAlta +
                ", domicilio='" + domicilio + '\'' +
                '}';
    }
}
