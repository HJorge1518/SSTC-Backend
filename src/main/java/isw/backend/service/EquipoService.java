package isw.backend.service;

import isw.backend.dto.EquipoRequest;
import isw.backend.dto.EquipoResponse;
import java.util.List;

public interface EquipoService {
    List<EquipoResponse> listarEquipos();
    EquipoResponse crearEquipo(EquipoRequest request);
}
