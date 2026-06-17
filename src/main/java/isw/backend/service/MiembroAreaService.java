package isw.backend.service;

import isw.backend.dto.MiembroAreaRequest;
import isw.backend.dto.MiembroAreaResponse;
import java.util.List;

public interface MiembroAreaService {
    List<MiembroAreaResponse> listarMiembrosArea();
    MiembroAreaResponse crearMiembroArea(MiembroAreaRequest request);
}
