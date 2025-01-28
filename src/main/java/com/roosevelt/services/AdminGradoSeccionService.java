package com.roosevelt.services;

import com.roosevelt.entities.GradoSeccion;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class AdminGradoSeccionService {

    /**
     * Retorna todos los registros de GradoSeccion ordenados por id.
     */
    public List<GradoSeccion> listAll() {
        return GradoSeccion.listAll(Sort.by("id"));
    }
}
