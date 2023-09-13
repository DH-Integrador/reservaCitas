package com.integrador.ReservaCitas.entity.Security;

import javax.persistence.*;
import lombok.*;

@Entity
@Data
public class Usuario {
    @Id
    private int id;

    private String nombre;

    private String username;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UsuarioRoleEnum rol;
}