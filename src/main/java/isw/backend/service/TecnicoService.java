package isw.backend.service;

import isw.backend.dto.TecnicoRequest;
import isw.backend.dto.TecnicoResponse;
import java.util.List;

public interface TecnicoService {
    List<TecnicoResponse> listarTecnicos();
    List<TecnicoResponse> listarTecnicosDisponibles();
    TecnicoResponse crearTecnico(TecnicoRequest request);
}
