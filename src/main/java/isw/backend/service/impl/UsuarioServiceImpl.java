package isw.backend.service.impl;

import isw.backend.dto.UsuarioRequest;
import isw.backend.dto.UsuarioResponse;
import isw.backend.model.Usuario;
import isw.backend.repository.UsuarioRepository;
import isw.backend.service.UsuarioService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponse> listarUsuarios() {
        return usuarioRepository.findAll().stream().map(UsuarioResponse::from).toList();
    }

    @Override
    public UsuarioResponse crearUsuario(UsuarioRequest request) {
        Usuario usuario = Usuario.builder()
                .nombre(request.nombre())
                .email(request.email())
                .area(request.area())
                .telefono(request.telefono())
                .build();
        return UsuarioResponse.from(usuarioRepository.save(usuario));
    }
}
