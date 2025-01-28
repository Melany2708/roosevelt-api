package com.roosevelt.dto.auth;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO para la petición de login (autenticación básica).
 */
public class LoginRequest {

    @NotBlank(message = "El email no puede estar vacío")
    public String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    public String password;

    public LoginRequest() {
    }

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
