package isw.backend.service;

import isw.backend.dto.FallaDiccionarioRequest;
import isw.backend.dto.FallaDiccionarioResponse;
import java.util.List;

public interface DiccionarioFallasService {
    List<FallaDiccionarioResponse> listar(String q);
    FallaDiccionarioResponse crear(FallaDiccionarioRequest request);
    FallaDiccionarioResponse actualizar(Long id, FallaDiccionarioRequest request);
}
