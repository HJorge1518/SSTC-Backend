package isw.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InformeTecnicoRequest(
        @NotBlank String descripcionSolucion,
        @NotNull Boolean requirioRepuesto
) {
}
