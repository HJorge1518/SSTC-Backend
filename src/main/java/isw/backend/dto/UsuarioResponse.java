package isw.backend.dto;

import isw.backend.model.Usuario;

public record UsuarioResponse(Long id, String nombre, String email, String area, String telefono) {
    public static UsuarioResponse from(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getArea(),
                usuario.getTelefono()
        );
    }
}
