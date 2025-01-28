package com.roosevelt.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

/**
 * Entidad que representa a un usuario del sistema:
 * - Administrador
 * - Profesor
 * - Estudiante
 */
@Entity
@Table(name = "users")
public class User extends PanacheEntity {

    public String nombre;

    @Column(unique = true)
    public String email;

    public String password;

    /**
     * Rol del usuario:
     * Puede ser "ADMIN", "PROFESOR" o "ESTUDIANTE".
     */
    public String rol;

    /**
     * Relación con GradoSeccion (solo aplica si el rol es ESTUDIANTE).
     */
    @ManyToOne
    @JoinColumn(name = "grado_seccion_id")
    public GradoSeccion gradoSeccion;

    // Método auxiliar para buscar por email
    public static User findByEmail(String email) {
        return find("email", email).firstResult();
    }
}
