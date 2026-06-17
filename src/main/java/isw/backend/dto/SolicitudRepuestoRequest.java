package isw.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SolicitudRepuestoRequest(
        @NotBlank String repuesto,
        @NotNull @Min(1) Integer cantidad
) {
}
