package com.roosevelt.resources.auth;

import com.roosevelt.dto.auth.AuthResponse;
import com.roosevelt.dto.auth.LoginRequest;
import com.roosevelt.services.AuthService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    AuthService authService;

    @POST
    @Path("/login")
    public Response login(@Valid LoginRequest request) {
        // Llama al servicio para validar credenciales
        AuthResponse response = authService.login(request);
        return Response.ok(response).build();
    }

    // (Opcional) Un endpoint para checking
    @GET
    @Path("/check")
    public Response check() {
        return Response.ok("Auth module is up and running").build();
    }
}
