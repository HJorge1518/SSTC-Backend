package isw.backend.dto;

public record TecnicoProductividadResponse(
        Long tecnicoId,
        String tecnico,
        long pendientes,
        long atendidasHoy,
        boolean disponible
) {
}
