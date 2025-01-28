package com.roosevelt.dto.admin;

import com.roosevelt.dto.admin.HorarioCursoDTO;
import com.roosevelt.dto.profesor.UserBasicDTO;
import com.roosevelt.entities.AsignacionProfesor;
import com.roosevelt.entities.HorarioCurso;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO para mostrar la info de un curso al profesor:
 * - Nombre, código
 * - Grado y sección
 * - Lista de horarios
 */
public class CursoAdminDTO {
    public Long id;
    public String nombre;
    public String codigo;
    public String grado;
    public String seccion;
    public List<HorarioCursoDTO> horarios; // Reutiliza HorarioCursoDTO
    public List<UserBasicDTO> profesores;

    public CursoAdminDTO() {
    }

    public CursoAdminDTO(Long id, String nombre, String codigo, String grado, String seccion,
                            List<HorarioCurso> horarios, List<AsignacionProfesor> asignacionProfesores) {
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
        this.profesores = asignacionProfesores.stream().map(ap -> {
            UserBasicDTO dto = new UserBasicDTO();
            dto.id = ap.profesor.id;
            dto.nombre = ap.profesor.nombre;
            dto.email = ap.profesor.email;
            return dto;
        }).collect(Collectors.toList());
    }
}
