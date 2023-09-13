package com.integrador.ReservaCitas.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ResourceNotFoundException extends RuntimeException{
    private final String resourceId;
    private final String resource;
}
