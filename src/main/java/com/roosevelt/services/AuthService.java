package com.roosevelt.services;

import com.roosevelt.dto.auth.AuthResponse;
import com.roosevelt.dto.auth.LoginRequest;
import com.roosevelt.entities.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class AuthService {

    /**
     * Verifica las credenciales (email, password).
     * Retorna un AuthResponse con nombre y rol si exitoso,
     * lanza excepción si no es válido.
     */
    @Transactional
    public AuthResponse login(LoginRequest request) {
        // Buscar el usuario en la base de datos
        User user = User.find("email", request.email).firstResult();
        if (user == null || !user.password.equals(request.password)) {
            // Podrías lanzar WebApplicationException con status 401
            throw new WebApplicationException("Credenciales inválidas", Response.Status.UNAUTHORIZED);
        }

        // Retornar nombre y rol
        return new AuthResponse(user.id, user.nombre, user.rol);
    }
}
