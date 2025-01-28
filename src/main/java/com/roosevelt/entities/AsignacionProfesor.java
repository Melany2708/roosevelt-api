package com.roosevelt.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

/**
 * Relaciona un profesor (User con rol=PROFESOR) con un curso.
 */
@Entity
@Table(name = "asignacion_profesor")
public class AsignacionProfesor extends PanacheEntity {

    @ManyToOne
    @JoinColumn(name = "profesor_id", nullable = false)
    public User profesor;

    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    public Curso curso;
}
