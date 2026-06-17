package isw.backend.service.impl;

import isw.backend.dto.MonitoreoResponse;
import isw.backend.dto.TecnicoProductividadResponse;
import isw.backend.model.EstadoIncidencia;
import isw.backend.model.Tecnico;
import isw.backend.repository.IncidenciaRepository;
import isw.backend.repository.InformeTecnicoRepository;
import isw.backend.repository.TecnicoRepository;
import isw.backend.service.ReporteService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ReporteServiceImpl implements ReporteService {
    private final IncidenciaRepository incidenciaRepository;
    private final InformeTecnicoRepository informeTecnicoRepository;
    private final TecnicoRepository tecnicoRepository;

    public ReporteServiceImpl(
            IncidenciaRepository incidenciaRepository,
            InformeTecnicoRepository informeTecnicoRepository,
            TecnicoRepository tecnicoRepository
    ) {
        this.incidenciaRepository = incidenciaRepository;
        this.informeTecnicoRepository = informeTecnicoRepository;
        this.tecnicoRepository = tecnicoRepository;
    }

    @Override
    public MonitoreoResponse monitoreoArea() {
        long pendientes = incidenciaRepository.countByEstado(EstadoIncidencia.PENDIENTE);
        long solucionadas = incidenciaRepository.countByEstado(EstadoIncidencia.SOLUCIONADO);
        List<TecnicoProductividadResponse> productividad = tecnicoRepository.findAll().stream()
                .map(this::productividad)
                .toList();
        return new MonitoreoResponse(pendientes, solucionadas, productividad);
    }

    private TecnicoProductividadResponse productividad(Tecnico tecnico) {
        long pendientes = incidenciaRepository.countByTecnicoAsignadoIdAndEstado(tecnico.getId(), EstadoIncidencia.PENDIENTE);
        LocalDate hoy = LocalDate.now();
        long atendidasHoy = informeTecnicoRepository.countByIncidenciaTecnicoAsignadoIdAndFechaSolucionBetween(
                tecnico.getId(),
                hoy.atStartOfDay(),
                hoy.plusDays(1).atStartOfDay()
        );
        return new TecnicoProductividadResponse(
                tecnico.getId(),
                tecnico.getNombre(),
                pendientes,
                atendidasHoy,
                Boolean.TRUE.equals(tecnico.getActivo()) && pendientes < tecnico.getLimiteAtencion()
        );
    }
}
