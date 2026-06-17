package isw.backend.service.impl;

import isw.backend.dto.TecnicoRequest;
import isw.backend.dto.TecnicoResponse;
import isw.backend.model.EstadoIncidencia;
import isw.backend.model.Tecnico;
import isw.backend.repository.IncidenciaRepository;
import isw.backend.repository.TecnicoRepository;
import isw.backend.service.TecnicoService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TecnicoServiceImpl implements TecnicoService {
    private final TecnicoRepository tecnicoRepository;
    private final IncidenciaRepository incidenciaRepository;

    public TecnicoServiceImpl(TecnicoRepository tecnicoRepository, IncidenciaRepository incidenciaRepository) {
        this.tecnicoRepository = tecnicoRepository;
        this.incidenciaRepository = incidenciaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TecnicoResponse> listarTecnicos() {
        return tecnicoRepository.findAll().stream().map(this::toTecnicoResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TecnicoResponse> listarTecnicosDisponibles() {
        return tecnicoRepository.findByActivoTrue().stream()
                .map(this::toTecnicoResponse)
                .filter(TecnicoResponse::disponible)
                .toList();
    }

    @Override
    public TecnicoResponse crearTecnico(TecnicoRequest request) {
        Tecnico tecnico = Tecnico.builder()
                .nombre(request.nombre())
                .email(request.email())
                .limiteAtencion(request.limiteAtencion())
                .activo(request.activo() == null || request.activo())
                .build();
        return toTecnicoResponse(tecnicoRepository.save(tecnico));
    }

    private TecnicoResponse toTecnicoResponse(Tecnico tecnico) {
        long pendientes = incidenciaRepository.countByTecnicoAsignadoIdAndEstado(tecnico.getId(), EstadoIncidencia.PENDIENTE);
        return TecnicoResponse.from(tecnico, pendientes);
    }
}
