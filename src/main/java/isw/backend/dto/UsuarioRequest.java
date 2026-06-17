package isw.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioRequest(
        @NotBlank String nombre,
        @Email @NotBlank String email,
        String area,
        String telefono
) {
}
