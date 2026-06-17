package isw.backend.dto;

import isw.backend.model.Tecnico;

public record TecnicoResponse(
        Long id,
        String nombre,
        String email,
        Integer limiteAtencion,
        Boolean activo,
        long incidenciasPendientes,
        boolean disponible
) {
    public static TecnicoResponse from(Tecnico tecnico, long pendientes) {
        return new TecnicoResponse(
                tecnico.getId(),
                tecnico.getNombre(),
                tecnico.getEmail(),
                tecnico.getLimiteAtencion(),
                tecnico.getActivo(),
                pendientes,
                Boolean.TRUE.equals(tecnico.getActivo()) && pendientes < tecnico.getLimiteAtencion()
        );
    }
}
