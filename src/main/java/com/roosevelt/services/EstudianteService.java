package com.roosevelt.services;

import com.roosevelt.entities.Curso;
import com.roosevelt.entities.GradoSeccion;
import com.roosevelt.entities.HorarioCurso;
import com.roosevelt.entities.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class EstudianteService {

    /**
     * Retorna la lista de cursos para el estudiante,
     * con base en su GradoSeccion.
     */
    public List<Curso> getCursosEstudiante(Long estudianteId) {
        User estudiante = User.findById(estudianteId);
        if (estudiante == null || !"ESTUDIANTE".equalsIgnoreCase(estudiante.rol)) {
            throw new WebApplicationException(
                    "El usuario con ID " + estudianteId + " no existe o no es ESTUDIANTE.",
                    Response.Status.BAD_REQUEST
            );
        }

        GradoSeccion gs = estudiante.gradoSeccion;
        if (gs == null) {
            // No tiene GradoSeccion => no hay cursos
            return List.of();
        }

        // Listar cursos del mismo gradoSeccion
        return Curso.list("gradoSeccion", gs);
    }

    /**
     * Retorna los horarios agrupados por día
     * de todos los cursos del estudiante.
     * Devuelve un Map<dia, List<HorarioCurso>> o algo similar,
     * según la lógica de tu DTO.
     */
    public Map<String, List<HorarioCurso>> getHorariosEstudiante(Long estudianteId) {
        User estudiante = User.findById(estudianteId);
        if (estudiante == null || !"ESTUDIANTE".equalsIgnoreCase(estudiante.rol)) {
            throw new WebApplicationException(
                    "El usuario con ID " + estudianteId + " no existe o no es ESTUDIANTE.",
                    Response.Status.BAD_REQUEST
            );
        }

        GradoSeccion gs = estudiante.gradoSeccion;
        if (gs == null) {
            // Sin gradoSeccion => no hay horarios
            return Collections.emptyMap();
        }

        List<Curso> cursos = Curso.list("gradoSeccion", gs);
        if (cursos.isEmpty()) {
            return Collections.emptyMap();
        }

        // Recolectar todos los HorarioCurso y agrupar por dia
        // Map<dia, List<HorarioCurso>>
        Map<String, List<HorarioCurso>> mapHorarios = new HashMap<>();

        for (Curso curso : cursos) {
            if (curso.horarios == null) continue;

            for (HorarioCurso hc : curso.horarios) {
                mapHorarios.putIfAbsent(hc.dia, new ArrayList<>());
                mapHorarios.get(hc.dia).add(hc);
            }
        }

        // Opcional: ordenar cada lista por horaInicio
        for (Map.Entry<String, List<HorarioCurso>> entry : mapHorarios.entrySet()) {
            entry.getValue().sort(Comparator.comparing(h -> h.horaInicio));
        }

        return mapHorarios;
    }
}
