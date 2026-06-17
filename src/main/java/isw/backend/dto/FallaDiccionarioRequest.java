package isw.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record FallaDiccionarioRequest(
        @NotBlank String titulo,
        @NotBlank String sintomas,
        @NotBlank String solucionRecomendada,
        String categoria
) {
}
