package com.integrador.ReservaCitas.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "TABLA_ODONTOLOGOS")
@Getter
@Setter
public class Odontologo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String matricula;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "APELLIDO")
    private String apellido;

    @OneToMany(mappedBy = "odontologo", cascade = CascadeType.ALL)
    private List<Turno> turnos;
}
