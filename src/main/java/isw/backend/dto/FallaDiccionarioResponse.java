package isw.backend.dto;

import isw.backend.model.FallaDiccionario;

public record FallaDiccionarioResponse(
        Long id,
        String titulo,
        String sintomas,
        String solucionRecomendada,
        String categoria
) {
    public static FallaDiccionarioResponse from(FallaDiccionario falla) {
        return new FallaDiccionarioResponse(
                falla.getId(),
                falla.getTitulo(),
                falla.getSintomas(),
                falla.getSolucionRecomendada(),
                falla.getCategoria()
        );
    }
}
