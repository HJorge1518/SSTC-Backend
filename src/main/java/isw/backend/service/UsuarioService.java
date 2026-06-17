package isw.backend.service;

import isw.backend.dto.UsuarioRequest;
import isw.backend.dto.UsuarioResponse;
import java.util.List;

public interface UsuarioService {
    List<UsuarioResponse> listarUsuarios();
    UsuarioResponse crearUsuario(UsuarioRequest request);
}
