package isw.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EquipoRequest(
        @NotBlank String codigo,
        @NotBlank String tipo,
        String marcaModelo,
        String ubicacion,
        @NotNull Long responsableId
) {
}
