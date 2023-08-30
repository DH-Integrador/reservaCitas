package com.integrador.ReservaCitas.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TABLA_DOMICILIOS")
@Getter
@Setter
public class Domicilio {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE)
    private int id;

    @Column(name = "CALLE")
    private String calle;

    @Column(name = "NUMERO")
    private String numero;

    @Column(name = "LOCALIDAD")
    private String localidad;

    @Column(name = "PROVINCIA")
    private String provincia;
}
