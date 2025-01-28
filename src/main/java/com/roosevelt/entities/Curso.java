package com.roosevelt.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.util.List;

/**
 * Entidad para manejar información de un curso.
 * Relacionada a un GradoSeccion y contiene varios HorarioCurso.
 */
@Entity
@Table(name = "cursos")
public class Curso extends PanacheEntity {


    public String nombre;

    @Column(unique = true)
    public String codigo;

    // Relación con el grado-sección al cual pertenece
    @ManyToOne
    @JoinColumn(name = "grado_seccion_id", nullable = false)
    public GradoSeccion gradoSeccion;

    /**
     * Relación OneToMany con HorarioCurso, para los días/horas.
     * - orphanRemoval = true si quieres eliminar automáticamente HorarioCurso
     *   cuando se quitan de la lista.
     */
    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<HorarioCurso> horarios;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    public List<AsignacionProfesor> asignaciones;
}

