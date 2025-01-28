package com.roosevelt.services;

import com.roosevelt.dto.admin.CursoAdminDTO;
import com.roosevelt.dto.admin.CursoDTO;
import com.roosevelt.dto.admin.GradoSeccionDTO;
import com.roosevelt.dto.admin.HorarioCursoDTO;
import com.roosevelt.entities.*;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class AdminCursoService {

    // LISTAR CURSOS
    public List<CursoAdminDTO> listAllCursos() {
        List<Curso> cursos = Curso.listAll(Sort.by("id"));
        return cursos.stream()
                .map(curso -> new CursoAdminDTO(
                        curso.id,
                        curso.nombre,
                        curso.codigo,
                        curso.gradoSeccion.grado,
                        curso.gradoSeccion.seccion,
                        curso.horarios,
                        curso.asignaciones
                ))
                .collect(Collectors.toList());
    }

    // CREAR CURSO
    @Transactional
    public Curso createCurso(CursoDTO dto) {
        // Verificar duplicado de código
        long count = Curso.count("codigo", dto.codigo);
        if (count > 0) {
            throw new WebApplicationException("Ya existe un curso con el código: " + dto.codigo,
                    Response.Status.CONFLICT);
        }

        Curso curso = new Curso();
        curso.nombre = dto.nombre;
        curso.codigo = dto.codigo;

        // Manejar GradoSeccion
        GradoSeccion gs = handleGradoSeccion(dto.gradoSeccion);
        curso.gradoSeccion = gs;

        curso.persist(); // Persistimos para obtener el ID

        // Manejo de horarios
        handleHorarios(curso, dto.horarios);

        // Asignar profesores
        handleProfesores(curso, dto.profesoresIds);

        curso = Curso.findById(curso.id);

        return curso;
    }

    // ACTUALIZAR CURSO
    @Transactional
    public CursoAdminDTO updateCurso(Long id, CursoDTO dto) {
        Curso curso = Curso.findById(id);
        if (curso == null) {
            throw new WebApplicationException("Curso no encontrado con id: " + id,
                    Response.Status.NOT_FOUND);
        }

        // Verificar colisión de código
        if (!curso.codigo.equals(dto.codigo)) {
            long countCodigo = Curso.count("codigo", dto.codigo);
            if (countCodigo > 0) {
                throw new WebApplicationException("Ya existe un curso con el código: " + dto.codigo,
                        Response.Status.CONFLICT);
            }
            curso.codigo = dto.codigo;
        }

        curso.nombre = dto.nombre;

        // Manejar GradoSeccion
        GradoSeccion gs = handleGradoSeccion(dto.gradoSeccion);
        curso.gradoSeccion = gs;
        curso.persist();

        // Eliminar horarios anteriores
        HorarioCurso.delete("curso", curso);

        // Crear nuevos horarios
        handleHorarios(curso, dto.horarios);

        // Eliminar asignaciones de profesores anteriores
        AsignacionProfesor.delete("curso", curso);

        // Asignar profesores nuevos
        handleProfesores(curso, dto.profesoresIds);

        // Forzar el fetch de la colección lazy antes de salir de la transacción
        Curso cursoActualizado = Curso.find("""
            SELECT c
            FROM Curso c
            LEFT JOIN FETCH c.horarios
            WHERE c.id = ?1
        """, id).firstResult();

        cursoActualizado.asignaciones = AsignacionProfesor.find("""
            SELECT ap
            FROM AsignacionProfesor ap
            LEFT JOIN FETCH ap.profesor
            WHERE ap.curso.id = ?1
        """, id).list();

        return  new CursoAdminDTO(
                cursoActualizado.id,
                cursoActualizado.nombre,
                cursoActualizado.codigo,
                cursoActualizado.gradoSeccion.grado,
                cursoActualizado.gradoSeccion.seccion,
                cursoActualizado.horarios,
                cursoActualizado.asignaciones
                );
    }

    // ELIMINAR CURSO
    @Transactional
    public void deleteCurso(Long id) {
        Curso curso = Curso.findById(id);
        if (curso == null) {
            throw new WebApplicationException("Curso no encontrado con id: " + id,
                    Response.Status.NOT_FOUND);
        }

        // Eliminar relaciones
        AsignacionProfesor.delete("curso", curso);
        HorarioCurso.delete("curso", curso);

        curso.delete();
    }

    // --------------------------------------------------------
    // METODOS AUXILIARES
    // --------------------------------------------------------
    private GradoSeccion handleGradoSeccion(GradoSeccionDTO gsDto) {
        if (gsDto == null) {
            throw new WebApplicationException("Se requiere un gradoSeccion en el Curso",
                    Response.Status.BAD_REQUEST);
        }
        if (gsDto.id != null) {
            GradoSeccion existing = GradoSeccion.findById(gsDto.id);
            if (existing == null) {
                throw new WebApplicationException(
                        "No existe el GradoSeccion con id: " + gsDto.id,
                        Response.Status.NOT_FOUND);
            }
            return existing;
        } else {
            GradoSeccion newGs = new GradoSeccion();
            newGs.grado = gsDto.grado;
            newGs.seccion = gsDto.seccion;
            newGs.persist();
            return newGs;
        }
    }

    private void handleHorarios(Curso curso, List<HorarioCursoDTO> listaHorarios) {
        if (listaHorarios == null || listaHorarios.isEmpty()) {
            return;
        }

        for (HorarioCursoDTO hcDto : listaHorarios) {
            HorarioCurso hc = new HorarioCurso();
            hc.dia = hcDto.dia;
            hc.horaInicio = hcDto.horaInicio;
            hc.horaFin = hcDto.horaFin;
            hc.curso = curso;
            hc.persist();
        }
    }

    private void handleProfesores(Curso curso, List<Long> profesoresIds) {
        if (profesoresIds == null || profesoresIds.isEmpty()) {
            return;
        }

        for (Long profesorId : profesoresIds) {
            User profesor = User.findById(profesorId);
            if (profesor == null || !"PROFESOR".equalsIgnoreCase(profesor.rol)) {
                throw new WebApplicationException(
                        "El usuario con id " + profesorId + " no existe o no es PROFESOR",
                        Response.Status.BAD_REQUEST
                );
            }

            AsignacionProfesor ap = new AsignacionProfesor();
            ap.profesor = profesor;
            ap.curso = curso;
            ap.persist();
        }
    }
}
