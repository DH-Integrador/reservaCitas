package com.integrador.ReservaCitas.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "TABLA_TURNOS")
@Getter
@Setter
public class Turno {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ODONTOLOGO_ID", nullable = false)
    private Odontologo odontologo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PACIENTE_ID", nullable = false)
    private Paciente paciente;

    @Column(name = "FECHA")
    private Date fecha;

    @Column(name = "HORA")
    private int hora;
}
