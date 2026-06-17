package isw.backend.dto;

import isw.backend.model.CanalRegistro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record IncidenciaRequest(
        @NotBlank String codigoEquipo,
        @NotBlank String problema,
        @NotNull CanalRegistro canalRegistro,
        @NotNull Long registradoPorId,
        String diagnosticoInicial
) {
}
