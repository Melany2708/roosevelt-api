package com.roosevelt.resources.estudiante;

import com.roosevelt.dto.estudiante.CursoEstudianteDTO;
import com.roosevelt.dto.estudiante.DetalleHorarioDTO;
import com.roosevelt.dto.estudiante.HorarioEstudianteDTO;
import com.roosevelt.entities.Curso;
import com.roosevelt.entities.HorarioCurso;
import com.roosevelt.services.EstudianteService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.*;
import java.util.stream.Collectors;

@Path("/estudiante")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EstudianteResource {

    @Inject
    EstudianteService estudianteService;

    // 1. LISTAR CURSOS
    @GET
    @Path("/{estudianteId}/cursos")
    public Response getCursosEstudiante(@PathParam("estudianteId") Long estudianteId) {
        List<Curso> cursos = estudianteService.getCursosEstudiante(estudianteId);

        // Convertir a DTO
        List<CursoEstudianteDTO> result = cursos.stream()
                .map(CursoEstudianteDTO::new)
                .collect(Collectors.toList());

        return Response.ok(result).build();
    }

    // 2. LISTAR HORARIOS (agrupados por día)
    @GET
    @Path("/{estudianteId}/horarios")
    public Response getHorariosEstudiante(@PathParam("estudianteId") Long estudianteId) {

        Map<String, List<HorarioCurso>> mapHorarios = estudianteService.getHorariosEstudiante(estudianteId);

        // Transformar a HorarioEstudianteDTO
        // mapHorarios: { "Lunes": [HorarioCurso, ...], "Martes": [HorarioCurso, ...] }
        Map<String, List<DetalleHorarioDTO>> horariosDTO = new HashMap<>();
        for (Map.Entry<String, List<HorarioCurso>> entry : mapHorarios.entrySet()) {
            String dia = entry.getKey();
            List<HorarioCurso> listaHC = entry.getValue();

            List<DetalleHorarioDTO> dtos = listaHC.stream()
                    .map(hc -> new DetalleHorarioDTO(
                            hc.curso.id,
                            hc.curso.nombre,
                            hc.horaInicio,
                            hc.horaFin
                    ))
                    .collect(Collectors.toList());

            horariosDTO.put(dia, dtos);
        }

        HorarioEstudianteDTO responseDto = new HorarioEstudianteDTO(horariosDTO);
        return Response.ok(responseDto).build();
    }
}
