package isw.backend.dto;

import isw.backend.model.CanalRegistro;
import isw.backend.model.EstadoIncidencia;
import isw.backend.model.Incidencia;
import java.time.LocalDateTime;

public record IncidenciaResponse(
        Long id,
        EquipoResponse equipo,
        String problema,
        CanalRegistro canalRegistro,
        LocalDateTime fechaRegistro,
        MiembroAreaResponse registradoPor,
        TecnicoResponse tecnicoAsignado,
        EstadoIncidencia estado,
        String diagnosticoInicial
) {
    public static IncidenciaResponse from(Incidencia incidencia, long pendientesTecnico) {
        return new IncidenciaResponse(
                incidencia.getId(),
                EquipoResponse.from(incidencia.getEquipo()),
                incidencia.getProblema(),
                incidencia.getCanalRegistro(),
                incidencia.getFechaRegistro(),
                MiembroAreaResponse.from(incidencia.getRegistradoPor()),
                incidencia.getTecnicoAsignado() == null ? null : TecnicoResponse.from(incidencia.getTecnicoAsignado(), pendientesTecnico),
                incidencia.getEstado(),
                incidencia.getDiagnosticoInicial()
        );
    }
}
