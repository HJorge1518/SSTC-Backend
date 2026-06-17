package isw.backend.dto;

import isw.backend.model.MiembroArea;
import isw.backend.model.RolMiembro;

public record MiembroAreaResponse(Long id, String nombre, RolMiembro rol) {
    public static MiembroAreaResponse from(MiembroArea miembro) {
        return new MiembroAreaResponse(miembro.getId(), miembro.getNombre(), miembro.getRol());
    }
}
