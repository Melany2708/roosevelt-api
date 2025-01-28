package com.roosevelt.dto.estudiante;

import com.roosevelt.entities.Curso;

/**
 * DTO sencillo para mostrar al estudiante la información de sus cursos.
 */
public class CursoEstudianteDTO {

    public Long id;
    public String nombre;
    public String codigo;
    public String grado;
    public String seccion;

    public CursoEstudianteDTO() {
    }

    public CursoEstudianteDTO(Curso curso) {
        this.id = curso.id;
        this.nombre = curso.nombre;
        this.codigo = curso.codigo;
        if (curso.gradoSeccion != null) {
            this.grado = curso.gradoSeccion.grado;
            this.seccion = curso.gradoSeccion.seccion;
        }
    }
}
