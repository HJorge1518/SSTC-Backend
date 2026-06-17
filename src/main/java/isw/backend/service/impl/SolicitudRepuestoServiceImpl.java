package isw.backend.service.impl;

import isw.backend.dto.SolicitudRepuestoRequest;
import isw.backend.dto.SolicitudRepuestoResponse;
import isw.backend.model.EstadoIncidencia;
import isw.backend.model.Incidencia;
import isw.backend.model.SolicitudRepuesto;
import isw.backend.repository.IncidenciaRepository;
import isw.backend.repository.SolicitudRepuestoRepository;
import isw.backend.service.SolicitudRepuestoService;
import isw.backend.util.BusinessRuleException;
import isw.backend.util.ResourceNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SolicitudRepuestoServiceImpl implements SolicitudRepuestoService {
    private final IncidenciaRepository incidenciaRepository;
    private final SolicitudRepuestoRepository solicitudRepuestoRepository;

    public SolicitudRepuestoServiceImpl(
            IncidenciaRepository incidenciaRepository,
            SolicitudRepuestoRepository solicitudRepuestoRepository
    ) {
        this.incidenciaRepository = incidenciaRepository;
        this.solicitudRepuestoRepository = solicitudRepuestoRepository;
    }

    @Override
    public SolicitudRepuestoResponse solicitarRepuesto(Long incidenciaId, SolicitudRepuestoRequest request) {
        Incidencia incidencia = incidenciaRepository.findById(incidenciaId)
                .orElseThrow(() -> new ResourceNotFoundException("No existe la incidencia indicada"));
        if (incidencia.getTecnicoAsignado() == null) {
            throw new BusinessRuleException("La incidencia debe estar asignada a un técnico antes de solicitar repuestos");
        }
        if (incidencia.getEstado() == EstadoIncidencia.SOLUCIONADO) {
            throw new BusinessRuleException("La incidencia ya fue solucionada");
        }
        SolicitudRepuesto solicitud = SolicitudRepuesto.builder()
                .incidencia(incidencia)
                .repuesto(request.repuesto())
                .cantidad(request.cantidad())
                .estadoLogistica("SOLICITADO")
                .build();
        return SolicitudRepuestoResponse.from(solicitudRepuestoRepository.save(solicitud));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SolicitudRepuestoResponse> listarSolicitudes(Long incidenciaId) {
        return solicitudRepuestoRepository.findByIncidenciaId(incidenciaId).stream()
                .map(SolicitudRepuestoResponse::from)
                .toList();
    }
}
