package isw.backend.dto;

import isw.backend.model.RolMiembro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MiembroAreaRequest(
        @NotBlank String nombre,
        @NotNull RolMiembro rol
) {
}
