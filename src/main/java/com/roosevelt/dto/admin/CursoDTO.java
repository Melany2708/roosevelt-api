package com.roosevelt.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * DTO para crear/actualizar un Curso.
 * Incluye:
 * - Información básica: nombre, código
 * - GradoSeccion (para crear/asignar)
 * - Lista de HorariosCurso a crear/asignar
 * - Lista de IDs de profesores a asignar
 */
public class CursoDTO {

    public Long id;

    @NotBlank(message = "El nombre del curso no puede estar vacío")
    @Size(max = 100, message = "El nombre del curso no debe exceder 100 caracteres")
    public String nombre;

    @NotBlank(message = "El código del curso no puede estar vacío")
    @Size(max = 20, message = "El código no debe exceder 20 caracteres")
    public String codigo;

    @NotNull(message = "El curso debe pertenecer a un grado y sección")
    public GradoSeccionDTO gradoSeccion;

    /**
     * Horarios (lista de HorarioCursoDTO) para crear/asignar al curso.
     */
    public List<HorarioCursoDTO> horarios;

    /**
     * Lista de IDs de los profesores que se asignarán al curso.
     */
    public List<Long> profesoresIds;

    public CursoDTO() {
    }
}
