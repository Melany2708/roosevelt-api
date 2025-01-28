package com.roosevelt.resources.admin;

import com.roosevelt.dto.admin.GradoSeccionListDTO;
import com.roosevelt.entities.GradoSeccion;
import com.roosevelt.services.AdminGradoSeccionService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/grados-secciones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminGradoSeccionResource {

    @Inject
    AdminGradoSeccionService adminGradoSeccionService;

    @GET
    public List<GradoSeccionListDTO> getAllGradosSecciones() {
        List<GradoSeccion> lista = adminGradoSeccionService.listAll();

        // Mapear cada GradoSeccion a GradoSeccionListDTO
        return lista.stream()
                .map(gs -> new GradoSeccionListDTO(gs.id, gs.grado, gs.seccion))
                .collect(Collectors.toList());
    }
}
