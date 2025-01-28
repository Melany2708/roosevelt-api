package com.roosevelt.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalTime;

/**
 * DTO para gestionar la creación/edición de un horario de curso
 * (día y horas de inicio-fin).
 */
public class HorarioCursoDTO {

    public Long id;

    @NotBlank(message = "El día no puede estar vacío")
    @Size(max = 20, message = "El día no debe exceder 20 caracteres")
    public String dia;

    @NotNull(message = "La hora de inicio no puede ser nula")
    public LocalTime horaInicio;

    @NotNull(message = "La hora de fin no puede ser nula")
    public LocalTime horaFin;

    public HorarioCursoDTO() {
    }
}
