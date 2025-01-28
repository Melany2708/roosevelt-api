package com.roosevelt.dto.profesor;

import com.roosevelt.dto.admin.HorarioCursoDTO;
import com.roosevelt.entities.HorarioCurso;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO para mostrar la info de un curso al profesor:
 * - Nombre, código
 * - Grado y sección
 * - Lista de horarios
 */
public class CursoProfesorDTO {
    public Long id;
    public String nombre;
    public String codigo;
    public String grado;
    public String seccion;
    public List<HorarioCursoDTO> horarios; // Reutiliza HorarioCursoDTO

    public CursoProfesorDTO() {
    }

    public CursoProfesorDTO(Long id, String nombre, String codigo, String grado, String seccion,
                            List<HorarioCurso> horarios) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.grado = grado;
        this.seccion = seccion;
        this.horarios = horarios.stream().map(hc -> {
            HorarioCursoDTO dto = new HorarioCursoDTO();
            dto.id = hc.id;
            dto.dia = hc.dia;
            dto.horaInicio = hc.horaInicio;
            dto.horaFin = hc.horaFin;
            return dto;
        }).collect(Collectors.toList());
    }
}
