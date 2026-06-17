package isw.backend.dto;

import isw.backend.model.InformeTecnico;
import java.time.LocalDateTime;

public record InformeTecnicoResponse(
        Long id,
        Long incidenciaId,
        String descripcionSolucion,
        Boolean requirioRepuesto,
        LocalDateTime fechaSolucion
) {
    public static InformeTecnicoResponse from(InformeTecnico informe) {
        return new InformeTecnicoResponse(
                informe.getId(),
                informe.getIncidencia().getId(),
                informe.getDescripcionSolucion(),
                informe.getRequirioRepuesto(),
                informe.getFechaSolucion()
        );
    }
}
