package isw.backend.service.impl;

import isw.backend.dto.InformeTecnicoRequest;
import isw.backend.dto.InformeTecnicoResponse;
import isw.backend.model.EstadoIncidencia;
import isw.backend.model.Incidencia;
import isw.backend.model.InformeTecnico;
import isw.backend.repository.IncidenciaRepository;
import isw.backend.repository.InformeTecnicoRepository;
import isw.backend.service.InformeTecnicoService;
import isw.backend.util.BusinessRuleException;
import isw.backend.util.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InformeTecnicoServiceImpl implements InformeTecnicoService {
    private final IncidenciaRepository incidenciaRepository;
    private final InformeTecnicoRepository informeTecnicoRepository;

    public InformeTecnicoServiceImpl(
            IncidenciaRepository incidenciaRepository,
            InformeTecnicoRepository informeTecnicoRepository
    ) {
        this.incidenciaRepository = incidenciaRepository;
        this.informeTecnicoRepository = informeTecnicoRepository;
    }

    @Override
    public InformeTecnicoResponse registrarInforme(Long incidenciaId, InformeTecnicoRequest request) {
        Incidencia incidencia = incidenciaRepository.findById(incidenciaId)
                .orElseThrow(() -> new ResourceNotFoundException("No existe la incidencia indicada"));
        if (incidencia.getTecnicoAsignado() == null) {
            throw new BusinessRuleException("La incidencia debe estar asignada antes de registrar el informe");
        }
        informeTecnicoRepository.findByIncidenciaId(incidenciaId).ifPresent(informe -> {
            throw new BusinessRuleException("La incidencia ya tiene informe técnico registrado");
        });
        InformeTecnico informe = InformeTecnico.builder()
                .incidencia(incidencia)
                .descripcionSolucion(request.descripcionSolucion())
                .requirioRepuesto(request.requirioRepuesto())
                .build();
        incidencia.setEstado(EstadoIncidencia.SOLUCIONADO);
        incidenciaRepository.save(incidencia);
        return InformeTecnicoResponse.from(informeTecnicoRepository.save(informe));
    }
}
