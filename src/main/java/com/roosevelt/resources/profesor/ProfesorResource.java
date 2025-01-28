package com.roosevelt.resources.profesor;

import com.roosevelt.dto.profesor.UserBasicDTO;
import com.roosevelt.dto.profesor.CursoProfesorDTO;
import com.roosevelt.dto.profesor.NotaDTO;
import com.roosevelt.entities.Notas;
import com.roosevelt.entities.User;
import com.roosevelt.services.ProfesorService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/profesor")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfesorResource {

    @Inject
    ProfesorService profesorService;

    // 1. LISTAR CURSOS DEL PROFESOR
    @GET
    @Path("/{profesorId}/cursos")
    public Response getCursosAsignados(@PathParam("profesorId") Long profesorId) {
        List <CursoProfesorDTO> cursos = profesorService.getCursosAsignados(profesorId);

        // Podrías devolver el set de `Curso` tal cual,
        // o mapear a un DTO específico.
        return Response.ok(cursos).build();
    }

    // 2. LISTAR ESTUDIANTES DE UN CURSO
    @GET
    @Path("/{profesorId}/cursos/{cursoId}/estudiantes")
    public Response getEstudiantesPorCurso(@PathParam("profesorId") Long profesorId,
                                           @PathParam("cursoId") Long cursoId) {
        List<User> estudiantes = profesorService.getEstudiantesPorCurso(profesorId, cursoId);
        // Convertir a un DTO básico para no exponer contraseñas, etc.
        List<UserBasicDTO> result = estudiantes.stream()
                .map(u -> new UserBasicDTO(u.id, u.nombre, u.email))
                .collect(Collectors.toList());
        return Response.ok(result).build();
    }

    // 3. REGISTRAR / EDITAR NOTA
    @POST
    @Path("/{profesorId}/cursos/{cursoId}/estudiantes/{estudianteId}/nota")
    public Response registrarNota(@PathParam("profesorId") Long profesorId,
                                  @PathParam("cursoId") Long cursoId,
                                  @PathParam("estudianteId") Long estudianteId,
                                  @Valid NotaDTO notaDto) {
        Notas nota = profesorService.registrarNota(profesorId, cursoId, estudianteId, notaDto);
        return Response.ok(nota).build();
    }
}
