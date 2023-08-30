package com.integrador.ReservaCitas.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "TABLA_PACIENTES")
@Getter
@Setter
public class Paciente {

    @Id
    private String dni;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "APELLIDO")
    private String apellido;

    @Column(name = "FECHA_ALTA")
    private Date fechaAlta;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "DOMICILIO_ID", referencedColumnName = "ID")
    private Domicilio domicilio;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Turno> turnos;
}
