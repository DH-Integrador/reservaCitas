package com.integrador.ReservaCitas.entity;
import javax.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Entity
@Getter
@Setter
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @ManyToOne
    @JoinColumn(name = "ODONTOLOGO_ID", nullable = false)
    private Odontologo odontologo;

    @ManyToOne
    @JoinColumn(name = "PACIENTE_ID", nullable = false)
    private Paciente paciente;

    @Column(name = "FECHA")
    private Date fecha;

    @Column(name = "HORA")
    private String hora;
}
