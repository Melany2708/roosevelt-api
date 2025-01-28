package com.roosevelt.dto.profesor;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para registrar o actualizar la nota de un estudiante en un curso.
 */
public class NotaDTO {

    @NotNull(message = "La nota no puede ser nula")
    @DecimalMin(value = "0.0", message = "La nota mínima es 0.0")
    @DecimalMax(value = "20.0", message = "La nota máxima es 20.0")
    public Double nota;

    public NotaDTO() {
    }

    public NotaDTO(Double nota) {
        this.nota = nota;
    }
}
