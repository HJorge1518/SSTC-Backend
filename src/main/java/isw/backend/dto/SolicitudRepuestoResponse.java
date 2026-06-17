package isw.backend.dto;

import isw.backend.model.SolicitudRepuesto;
import java.time.LocalDateTime;

public record SolicitudRepuestoResponse(
        Long id,
        Long incidenciaId,
        String repuesto,
        Integer cantidad,
        LocalDateTime fechaSolicitud,
        String estadoLogistica
) {
    public static SolicitudRepuestoResponse from(SolicitudRepuesto solicitud) {
        return new SolicitudRepuestoResponse(
                solicitud.getId(),
                solicitud.getIncidencia().getId(),
                solicitud.getRepuesto(),
                solicitud.getCantidad(),
                solicitud.getFechaSolicitud(),
                solicitud.getEstadoLogistica()
        );
    }
}
