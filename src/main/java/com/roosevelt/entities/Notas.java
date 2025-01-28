package com.roosevelt.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

/**
 * Representa la nota de un estudiante en un curso concreto.
 */
@Entity
@Table(name = "notas")
public class Notas extends PanacheEntity {

    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    public Curso curso;

    @ManyToOne
    @JoinColumn(name = "estudiante_id", nullable = false)
    public User estudiante;

    @NotNull(message = "La nota no puede ser nula")
    public Double nota;
}
