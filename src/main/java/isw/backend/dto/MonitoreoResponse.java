package isw.backend.dto;

import java.util.List;

public record MonitoreoResponse(
        long incidenciasPendientes,
        long incidenciasSolucionadas,
        List<TecnicoProductividadResponse> productividadTecnicos
) {
}
