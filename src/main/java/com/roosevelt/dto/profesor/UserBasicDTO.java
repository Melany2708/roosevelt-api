package com.roosevelt.dto.profesor;

/**
 * DTO básico para mostrar información de un estudiante
 * sin incluir la contraseña ni datos innecesarios.
 */
public class UserBasicDTO {
    public Long id;
    public String nombre;
    public String email;

    public UserBasicDTO() {
    }

    public UserBasicDTO(Long id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }
}
