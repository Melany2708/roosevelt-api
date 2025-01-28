package com.roosevelt.dto.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para crear/actualizar usuarios desde el módulo de administración.
 * - Si el rol es "ESTUDIANTE", se usará `gradoSeccion` para asignar o crear.
 */
public class UserDTO {

    public Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    public String nombre;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Formato de correo inválido")
    @Size(max = 120, message = "El correo no debe exceder 120 caracteres")
    public String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    public String password;

    @NotBlank(message = "El rol no puede estar vacío")
    @Size(max = 15, message = "El rol no debe exceder 15 caracteres")
    public String rol;

    /**
     * Se asignará o creará un GradoSeccion
     * solo si el rol es "ESTUDIANTE".
     */
    public GradoSeccionDTO gradoSeccion;

    public UserDTO() {
    }
}
