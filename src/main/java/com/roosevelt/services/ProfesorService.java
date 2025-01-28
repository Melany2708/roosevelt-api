package com.roosevelt.services;

import com.roosevelt.dto.profesor.CursoProfesorDTO;
import com.roosevelt.dto.profesor.NotaDTO;
import com.roosevelt.entities.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProfesorService {

    /**
     * Retorna la lista de cursos asignados a un profesor.
     */
    public List<CursoProfesorDTO> getCursosAsignados(Long profesorId) {
        User profesor = User.findById(profesorId);
        if (profesor == null || !"PROFESOR".equalsIgnoreCase(profesor.rol)) {
            throw new WebApplicationException(
                    "El usuario con ID " + profesorId + " no existe o no es PROFESOR",
                    Response.Status.BAD_REQUEST);
        }

        List<AsignacionProfesor> asignaciones = AsignacionProfesor.list("profesor", profesor);
        // Obtener el set de cursos
        Set<Curso> cursosUnicos = asignaciones.stream()
                .map(ap -> ap.curso)
                .collect(Collectors.toSet());

        return cursosUnicos.stream()
                .map(curso -> new CursoProfesorDTO(
                        curso.id,
                        curso.nombre,
                        curso.codigo,
                        curso.gradoSeccion.grado,
                        curso.gradoSeccion.seccion,
                        curso.horarios
                ))
                .collect(Collectors.toList());
    }

    /**
     * Lista a los estudiantes (Users con rol=ESTUDIANTE)
     * que pertenezcan al mismo GradoSeccion del curso.
     */
    public List<User> getEstudiantesPorCurso(Long profesorId, Long cursoId) {
        User profesor = validarProfesorCurso(profesorId, cursoId);

        Curso curso = Curso.findById(cursoId);
        GradoSeccion gs = curso.gradoSeccion;
        if (gs == null) {
            // Sin gradoSeccion => no hay estudiantes
            return List.of();
        }

        // Listar usuarios con rol=ESTUDIANTE y gradoSeccion = gs
        return User.list("rol = ?1 and gradoSeccion = ?2", "ESTUDIANTE", gs);
    }

    /**
     * Registra o actualiza la nota de un estudiante en un curso.
     */
    @Transactional
    public Notas registrarNota(Long profesorId, Long cursoId, Long estudianteId, NotaDTO notaDto) {
        // Validar profesor y curso
        validarProfesorCurso(profesorId, cursoId);

        // Validar estudiante
        User estudiante = User.findById(estudianteId);
        if (estudiante == null || !"ESTUDIANTE".equalsIgnoreCase(estudiante.rol)) {
            throw new WebApplicationException(
                    "El usuario con ID " + estudianteId + " no es un estudiante válido",
                    Response.Status.BAD_REQUEST
            );
        }

        Curso curso = Curso.findById(cursoId);
        // Chequear que el estudiante pertenezca al mismo GradoSeccion
        if (estudiante.gradoSeccion == null || !estudiante.gradoSeccion.equals(curso.gradoSeccion)) {
            throw new WebApplicationException(
                    "El estudiante no pertenece al grado-sección de este curso.",
                    Response.Status.FORBIDDEN
            );
        }

        // Crear/actualizar la nota
        Notas nota = Notas.find("curso = ?1 AND estudiante = ?2", curso, estudiante).firstResult();
        if (nota == null) {
            nota = new Notas();
            nota.curso = curso;
            nota.estudiante = estudiante;
        }
        nota.nota = notaDto.nota;
        nota.persist();

        return nota;
    }

    /**
     * Método auxiliar para validar que:
     * - El profesor existe y es rol=PROFESOR.
     * - El curso existe.
     * - El profesor está asignado a ese curso.
     */
    private User validarProfesorCurso(Long profesorId, Long cursoId) {
        User profesor = User.findById(profesorId);
        if (profesor == null || !"PROFESOR".equalsIgnoreCase(profesor.rol)) {
            throw new WebApplicationException(
                    "El usuario con ID " + profesorId + " no existe o no es PROFESOR",
                    Response.Status.BAD_REQUEST);
        }

        Curso curso = Curso.findById(cursoId);
        if (curso == null) {
            throw new WebApplicationException(
                    "No existe el curso con ID " + cursoId,
                    Response.Status.NOT_FOUND
            );
        }

        long count = AsignacionProfesor.count("profesor = ?1 AND curso = ?2", profesor, curso);
        if (count == 0) {
            throw new WebApplicationException(
                    "El profesor con ID " + profesorId + " no está asignado a este curso",
                    Response.Status.FORBIDDEN
            );
        }
        return profesor;
    }
}
