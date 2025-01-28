package com.roosevelt.dto.estudiante;

import java.time.LocalTime;

/**
 * DTO para detallar un horario dentro de un día:
 * - cursoId, cursoNombre, horaInicio, horaFin
 */
public class DetalleHorarioDTO {
    public Long cursoId;
    public String cursoNombre;
    public LocalTime horaInicio;
    public LocalTime horaFin;

    public DetalleHorarioDTO() {
    }

    public DetalleHorarioDTO(Long cursoId, String cursoNombre, LocalTime horaInicio, LocalTime horaFin) {
        this.cursoId = cursoId;
        this.cursoNombre = cursoNombre;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }
}
