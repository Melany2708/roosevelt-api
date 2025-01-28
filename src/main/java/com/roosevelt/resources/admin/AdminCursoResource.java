package com.roosevelt.resources.admin;

import com.roosevelt.dto.admin.CursoAdminDTO;
import com.roosevelt.dto.admin.CursoDTO;
import com.roosevelt.entities.Curso;
import com.roosevelt.services.AdminCursoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/cursos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminCursoResource {

    @Inject
    AdminCursoService adminCursoService;

    // LISTAR
    @GET
    public List<CursoAdminDTO> listAllCursos() {
        return adminCursoService.listAllCursos();
    }

    // CREAR
    @POST
    public Response createCurso(@Valid CursoDTO dto) {
        Curso created = adminCursoService.createCurso(dto);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    // ACTUALIZAR
    @PUT
    @Path("/{id}")
    public Response updateCurso(@PathParam("id") Long id, @Valid CursoDTO dto) {
        CursoAdminDTO updated = adminCursoService.updateCurso(id, dto);
        return Response.ok(updated).build();
    }

    // ELIMINAR
    @DELETE
    @Path("/{id}")
    public Response deleteCurso(@PathParam("id") Long id) {
        adminCursoService.deleteCurso(id);
        return Response.noContent().build();
    }
}
