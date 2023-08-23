package com.integrador.ReservaCitas.model;
import java.util.Date;

public class Turno {
    private String matriculaOdontologo;
    private String dniPaciente;
    private Date fecha;
    private int hora;

    public Turno() {
    }

    public Turno(String matriculaOdontologo, String dniPaciente, Date fecha, int hora) {
        this.matriculaOdontologo = matriculaOdontologo;
        this.dniPaciente = dniPaciente;
        this.fecha = fecha;
        this.hora = hora;
    }

    public String getMatriculaOdontologo() {
        return matriculaOdontologo;
    }

    public void setMatriculaOdontologo(String matriculaOdontologo) {
        this.matriculaOdontologo = matriculaOdontologo;
    }

    public String getDniPaciente() {
        return dniPaciente;
    }

    public void setDniPaciente(String dniPaciente) {
        this.dniPaciente = dniPaciente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "Turno{" +
                "matriculaOdontologo='" + matriculaOdontologo + '\'' +
                ", dniPaciente='" + dniPaciente + '\'' +
                ", fecha=" + fecha +
                ", hora=" + hora +
                '}';
    }
}
