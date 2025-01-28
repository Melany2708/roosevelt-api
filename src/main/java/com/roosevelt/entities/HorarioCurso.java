package com.roosevelt.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalTime;

/**
 * Define un horario (día y horas) para un curso específico.
 */
@Entity
@Table(name = "horario_curso")
public class HorarioCurso extends PanacheEntity {

    public String dia;

    @Column(name = "hora_inicio")
    public LocalTime horaInicio;

    @Column(name = "hora_fin")
    public LocalTime horaFin;

    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    public Curso curso;
}
