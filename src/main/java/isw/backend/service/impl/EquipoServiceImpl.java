package isw.backend.service.impl;

import isw.backend.dto.EquipoRequest;
import isw.backend.dto.EquipoResponse;
import isw.backend.model.Equipo;
import isw.backend.model.Usuario;
import isw.backend.repository.EquipoRepository;
import isw.backend.repository.UsuarioRepository;
import isw.backend.service.EquipoService;
import isw.backend.util.ResourceNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EquipoServiceImpl implements EquipoService {
    private final EquipoRepository equipoRepository;
    private final UsuarioRepository usuarioRepository;

    public EquipoServiceImpl(EquipoRepository equipoRepository, UsuarioRepository usuarioRepository) {
        this.equipoRepository = equipoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipoResponse> listarEquipos() {
        return equipoRepository.findAll().stream().map(EquipoResponse::from).toList();
    }

    @Override
    public EquipoResponse crearEquipo(EquipoRequest request) {
        Usuario responsable = usuarioRepository.findById(request.responsableId())
                .orElseThrow(() -> new ResourceNotFoundException("No existe el usuario responsable"));
        Equipo equipo = Equipo.builder()
                .codigo(request.codigo())
                .tipo(request.tipo())
                .marcaModelo(request.marcaModelo())
                .ubicacion(request.ubicacion())
                .responsable(responsable)
                .build();
        return EquipoResponse.from(equipoRepository.save(equipo));
    }
}
