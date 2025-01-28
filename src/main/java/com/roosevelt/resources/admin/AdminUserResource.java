package com.roosevelt.resources.admin;

import com.roosevelt.dto.admin.UserDTO;
import com.roosevelt.entities.User;
import com.roosevelt.services.AdminUserService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminUserResource {

    @Inject
    AdminUserService adminUserService;

    // -------------------------
    // LISTAR
    // -------------------------
    @GET
    public List<User> listAllUsers() {
        return adminUserService.listAllUsers();
    }

    @GET
    @Path("/admins")
    public List<User> listAdmins() {
        return adminUserService.listAdmins();
    }

    @GET
    @Path("/profesores")
    public List<User> listProfesores() {
        return adminUserService.listProfesores();
    }

    @GET
    @Path("/estudiantes")
    public List<User> listEstudiantes() {
        return adminUserService.listEstudiantes();
    }

    // -------------------------
    // CREAR
    // -------------------------
    @POST
    public Response createUser(@Valid UserDTO userDto) {
        User created = adminUserService.createUser(userDto);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    // -------------------------
    // ACTUALIZAR
    // -------------------------
    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") Long id, @Valid UserDTO userDto) {
        User updated = adminUserService.updateUser(id, userDto);
        return Response.ok(updated).build();
    }

    // -------------------------
    // ELIMINAR
    // -------------------------
    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        adminUserService.deleteUser(id);
        return Response.noContent().build();
    }
}
