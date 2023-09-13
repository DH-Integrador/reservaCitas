package com.integrador.ReservaCitas.dto;

import lombok.Getter;

@Getter
public class LoginDTO {
    private String username;
    private String password;

    public LoginDTO() {
    }

    public LoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
