package isw.backend.dto;

import isw.backend.model.Equipo;

public record EquipoResponse(
        Long id,
        String codigo,
        String tipo,
        String marcaModelo,
        String ubicacion,
        UsuarioResponse responsable
) {
    public static EquipoResponse from(Equipo equipo) {
        return new EquipoResponse(
                equipo.getId(),
                equipo.getCodigo(),
                equipo.getTipo(),
                equipo.getMarcaModelo(),
                equipo.getUbicacion(),
                UsuarioResponse.from(equipo.getResponsable())
        );
    }
}
