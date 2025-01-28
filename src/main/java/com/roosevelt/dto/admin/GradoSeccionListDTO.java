package com.roosevelt.dto.admin;

/**
 * DTO para listar GradoSeccion con el formato:
 * {
 *   "id": 1,
 *   "gradoSeccion": "1er Año A"
 * }
 */
public class GradoSeccionListDTO {

    public Long id;
    public String gradoSeccion;

    public GradoSeccionListDTO() {
    }

    public GradoSeccionListDTO(Long id, String grado, String seccion) {
        this.id = id;
        // Concatenamos 'grado' y 'seccion' en un solo string
        this.gradoSeccion = grado + " " + seccion;
    }
}
