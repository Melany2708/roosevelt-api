package com.roosevelt.dto.auth;

/**
 * DTO para la respuesta de login.
 */
public class AuthResponse {

    public long id;
    public String nombre;
    public String rol;

    public AuthResponse() {
    }

    public AuthResponse(long id, String nombre, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.rol = rol;
    }
}
