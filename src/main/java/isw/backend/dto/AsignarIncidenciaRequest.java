package isw.backend.dto;

import jakarta.validation.constraints.NotNull;

public record AsignarIncidenciaRequest(@NotNull Long tecnicoId) {
}
