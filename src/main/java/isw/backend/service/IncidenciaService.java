package isw.backend.service;

import isw.backend.dto.AsignarIncidenciaRequest;
import isw.backend.dto.IncidenciaRequest;
import isw.backend.dto.IncidenciaResponse;
import java.util.List;

public interface IncidenciaService {
    List<IncidenciaResponse> listar();
    IncidenciaResponse buscar(Long id);
    IncidenciaResponse registrar(IncidenciaRequest request);
    IncidenciaResponse asignar(Long incidenciaId, AsignarIncidenciaRequest request);
    List<IncidenciaResponse> tareasAsignadas(Long tecnicoId);
    List<IncidenciaResponse> historialEquipo(String codigoEquipo);
}
