package isw.backend.service;

import isw.backend.dto.InformeTecnicoRequest;
import isw.backend.dto.InformeTecnicoResponse;

public interface InformeTecnicoService {
    InformeTecnicoResponse registrarInforme(Long incidenciaId, InformeTecnicoRequest request);
}
