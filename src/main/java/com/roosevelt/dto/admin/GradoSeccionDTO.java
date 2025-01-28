package com.roosevelt.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para crear/actualizar un GradoSeccion
 * (usado cuando se asigne al usuario o al curso).
 */
public class GradoSeccionDTO {

    public Long id;

    @NotBlank(message = "El grado no puede estar vacío")
    @Size(max = 50, message = "El grado no debe exceder 50 caracteres")
    public String grado;

    @NotBlank(message = "La sección no puede estar vacía")
    @Size(max = 50, message = "La sección no debe exceder 50 caracteres")
    public String seccion;

    public GradoSeccionDTO() {
    }
}
