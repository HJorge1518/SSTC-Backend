package isw.backend.service;

import isw.backend.dto.EquipoRequest;
import isw.backend.dto.EquipoResponse;
import isw.backend.dto.MiembroAreaRequest;
import isw.backend.dto.MiembroAreaResponse;
import isw.backend.dto.TecnicoRequest;
import isw.backend.dto.TecnicoResponse;
import isw.backend.dto.UsuarioRequest;
import isw.backend.dto.UsuarioResponse;
import isw.backend.model.Equipo;
import isw.backend.model.EstadoIncidencia;
import isw.backend.model.MiembroArea;
import isw.backend.model.Tecnico;
import isw.backend.model.Usuario;
import isw.backend.repository.EquipoRepository;
import isw.backend.repository.IncidenciaRepository;
import isw.backend.repository.MiembroAreaRepository;
import isw.backend.repository.TecnicoRepository;
import isw.backend.repository.UsuarioRepository;
import isw.backend.util.ResourceNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CatalogoService {
    private final UsuarioRepository usuarioRepository;
    private final EquipoRepository equipoRepository;
    private final TecnicoRepository tecnicoRepository;
    private final MiembroAreaRepository miembroAreaRepository;
    private final IncidenciaRepository incidenciaRepository;

    public CatalogoService(
            UsuarioRepository usuarioRepository,
            EquipoRepository equipoRepository,
            TecnicoRepository tecnicoRepository,
            MiembroAreaRepository miembroAreaRepository,
            IncidenciaRepository incidenciaRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.equipoRepository = equipoRepository;
        this.tecnicoRepository = tecnicoRepository;
        this.miembroAreaRepository = miembroAreaRepository;
        this.incidenciaRepository = incidenciaRepository;
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponse> listarUsuarios() {
        return usuarioRepository.findAll().stream().map(UsuarioResponse::from).toList();
    }

    public UsuarioResponse crearUsuario(UsuarioRequest request) {
        Usuario usuario = Usuario.builder()
                .nombre(request.nombre())
                .email(request.email())
                .area(request.area())
                .telefono(request.telefono())
                .build();
        return UsuarioResponse.from(usuarioRepository.save(usuario));
    }

    @Transactional(readOnly = true)
    public List<EquipoResponse> listarEquipos() {
        return equipoRepository.findAll().stream().map(EquipoResponse::from).toList();
    }

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

    @Transactional(readOnly = true)
    public List<TecnicoResponse> listarTecnicos() {
        return tecnicoRepository.findAll().stream().map(this::toTecnicoResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<TecnicoResponse> listarTecnicosDisponibles() {
        return tecnicoRepository.findByActivoTrue().stream()
                .map(this::toTecnicoResponse)
                .filter(TecnicoResponse::disponible)
                .toList();
    }

    public TecnicoResponse crearTecnico(TecnicoRequest request) {
        Tecnico tecnico = Tecnico.builder()
                .nombre(request.nombre())
                .email(request.email())
                .limiteAtencion(request.limiteAtencion())
                .activo(request.activo() == null || request.activo())
                .build();
        return toTecnicoResponse(tecnicoRepository.save(tecnico));
    }

    @Transactional(readOnly = true)
    public List<MiembroAreaResponse> listarMiembrosArea() {
        return miembroAreaRepository.findAll().stream().map(MiembroAreaResponse::from).toList();
    }

    public MiembroAreaResponse crearMiembroArea(MiembroAreaRequest request) {
        MiembroArea miembro = MiembroArea.builder()
                .nombre(request.nombre())
                .rol(request.rol())
                .build();
        return MiembroAreaResponse.from(miembroAreaRepository.save(miembro));
    }

    private TecnicoResponse toTecnicoResponse(Tecnico tecnico) {
        long pendientes = incidenciaRepository.countByTecnicoAsignadoIdAndEstado(tecnico.getId(), EstadoIncidencia.PENDIENTE);
        return TecnicoResponse.from(tecnico, pendientes);
    }
}
