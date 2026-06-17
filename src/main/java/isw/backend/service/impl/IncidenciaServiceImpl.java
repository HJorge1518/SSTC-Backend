package isw.backend.service.impl;

import isw.backend.dto.AsignarIncidenciaRequest;
import isw.backend.dto.IncidenciaRequest;
import isw.backend.dto.IncidenciaResponse;
import isw.backend.model.Equipo;
import isw.backend.model.EstadoIncidencia;
import isw.backend.model.Incidencia;
import isw.backend.model.MiembroArea;
import isw.backend.model.Tecnico;
import isw.backend.repository.EquipoRepository;
import isw.backend.repository.IncidenciaRepository;
import isw.backend.repository.MiembroAreaRepository;
import isw.backend.repository.TecnicoRepository;
import isw.backend.service.IncidenciaService;
import isw.backend.util.BusinessRuleException;
import isw.backend.util.ResourceNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class IncidenciaServiceImpl implements IncidenciaService {
    private final IncidenciaRepository incidenciaRepository;
    private final EquipoRepository equipoRepository;
    private final MiembroAreaRepository miembroAreaRepository;
    private final TecnicoRepository tecnicoRepository;

    public IncidenciaServiceImpl(
            IncidenciaRepository incidenciaRepository,
            EquipoRepository equipoRepository,
            MiembroAreaRepository miembroAreaRepository,
            TecnicoRepository tecnicoRepository
    ) {
        this.incidenciaRepository = incidenciaRepository;
        this.equipoRepository = equipoRepository;
        this.miembroAreaRepository = miembroAreaRepository;
        this.tecnicoRepository = tecnicoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<IncidenciaResponse> listar() {
        return incidenciaRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public IncidenciaResponse buscar(Long id) {
        return toResponse(getIncidencia(id));
    }

    @Override
    public IncidenciaResponse registrar(IncidenciaRequest request) {
        Equipo equipo = equipoRepository.findByCodigoIgnoreCase(request.codigoEquipo())
                .orElseThrow(() -> new ResourceNotFoundException("No existe un equipo con codigo " + request.codigoEquipo()));
        MiembroArea registradoPor = miembroAreaRepository.findById(request.registradoPorId())
                .orElseThrow(() -> new ResourceNotFoundException("No existe la persona que registra la incidencia"));
        Incidencia incidencia = Incidencia.builder()
                .equipo(equipo)
                .problema(request.problema())
                .canalRegistro(request.canalRegistro())
                .registradoPor(registradoPor)
                .diagnosticoInicial(request.diagnosticoInicial())
                .estado(EstadoIncidencia.PENDIENTE)
                .build();
        return toResponse(incidenciaRepository.save(incidencia));
    }

    @Override
    public IncidenciaResponse asignar(Long incidenciaId, AsignarIncidenciaRequest request) {
        Incidencia incidencia = getIncidencia(incidenciaId);
        if (incidencia.getEstado() == EstadoIncidencia.SOLUCIONADO) {
            throw new BusinessRuleException("No se puede asignar una incidencia solucionada");
        }
        Tecnico tecnico = tecnicoRepository.findById(request.tecnicoId())
                .orElseThrow(() -> new ResourceNotFoundException("No existe el tecnico indicado"));
        long pendientes = incidenciaRepository.countByTecnicoAsignadoIdAndEstado(tecnico.getId(), EstadoIncidencia.PENDIENTE);
        boolean mismaIncidencia = incidencia.getTecnicoAsignado() != null
                && incidencia.getTecnicoAsignado().getId().equals(tecnico.getId());
        if (!Boolean.TRUE.equals(tecnico.getActivo())) {
            throw new BusinessRuleException("El tecnico no esta activo");
        }
        if (!mismaIncidencia && pendientes >= tecnico.getLimiteAtencion()) {
            throw new BusinessRuleException("El tecnico alcanzo su limite de atencion");
        }
        incidencia.setTecnicoAsignado(tecnico);
        return toResponse(incidenciaRepository.save(incidencia));
    }

    @Override
    @Transactional(readOnly = true)
    public List<IncidenciaResponse> tareasAsignadas(Long tecnicoId) {
        if (!tecnicoRepository.existsById(tecnicoId)) {
            throw new ResourceNotFoundException("No existe el tecnico indicado");
        }
        return incidenciaRepository.findByTecnicoAsignadoIdOrderByFechaRegistroDesc(tecnicoId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<IncidenciaResponse> historialEquipo(String codigoEquipo) {
        if (equipoRepository.findByCodigoIgnoreCase(codigoEquipo).isEmpty()) {
            throw new ResourceNotFoundException("No existe un equipo con codigo " + codigoEquipo);
        }
        return incidenciaRepository.findByEquipoCodigoIgnoreCaseOrderByFechaRegistroDesc(codigoEquipo)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private Incidencia getIncidencia(Long id) {
        return incidenciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe la incidencia indicada"));
    }

    private IncidenciaResponse toResponse(Incidencia incidencia) {
        long pendientesTecnico = incidencia.getTecnicoAsignado() == null
                ? 0
                : incidenciaRepository.countByTecnicoAsignadoIdAndEstado(incidencia.getTecnicoAsignado().getId(), EstadoIncidencia.PENDIENTE);
        return IncidenciaResponse.from(incidencia, pendientesTecnico);
    }
}
