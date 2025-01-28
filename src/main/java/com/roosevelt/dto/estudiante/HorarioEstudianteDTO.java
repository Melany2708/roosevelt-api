package com.roosevelt.dto.estudiante;

import java.util.List;
import java.util.Map;

/**
 * DTO para representar los horarios del estudiante agrupados por día:
 * {
 *   "horarios": {
 *       "Lunes": [ DetalleHorarioDTO, DetalleHorarioDTO ],
 *       "Martes": [ ... ],
 *       ...
 *   }
 * }
 */
public class HorarioEstudianteDTO {

    /**
     * Clave = Día (ej. "Lunes", "Martes", etc.)
     * Valor = lista de DetalleHorarioDTO
     */
    public Map<String, List<DetalleHorarioDTO>> horarios;

    public HorarioEstudianteDTO() {
    }

    public HorarioEstudianteDTO(Map<String, List<DetalleHorarioDTO>> horarios) {
        this.horarios = horarios;
    }
}
