package com.roosevelt.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

/**
 * Entidad que representa un grado y sección,
 * a la que puede pertenecer un estudiante.
 */
@Entity
@Table(name = "grado_seccion")
public class GradoSeccion extends PanacheEntity {

    public String grado;

    public String seccion;

    // Si deseas ver la lista de estudiantes, podrías habilitar la relación inversa:
    // @OneToMany(mappedBy = "gradoSeccion")
    // public List<User> estudiantes;
}
