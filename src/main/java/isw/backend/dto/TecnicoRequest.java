package isw.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TecnicoRequest(
        @NotBlank String nombre,
        @Email @NotBlank String email,
        @NotNull @Min(1) Integer limiteAtencion,
        Boolean activo
) {
}
