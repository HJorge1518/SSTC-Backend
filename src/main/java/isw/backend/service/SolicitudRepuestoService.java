package isw.backend.service;

import isw.backend.dto.SolicitudRepuestoRequest;
import isw.backend.dto.SolicitudRepuestoResponse;
import java.util.List;

public interface SolicitudRepuestoService {
    SolicitudRepuestoResponse solicitarRepuesto(Long incidenciaId, SolicitudRepuestoRequest request);
    List<SolicitudRepuestoResponse> listarSolicitudes(Long incidenciaId);
}
