package isw.backend.controller;

import isw.backend.dto.IncidenciaResponse;
import isw.backend.dto.MonitoreoResponse;
import isw.backend.service.IncidenciaService;
import isw.backend.service.ReporteService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reportes")
public class ReporteController {
    private final ReporteService reporteService;
    private final IncidenciaService incidenciaService;

    public ReporteController(ReporteService reporteService, IncidenciaService incidenciaService) {
        this.reporteService = reporteService;
        this.incidenciaService = incidenciaService;
    }

    @GetMapping("/monitoreo")
    public MonitoreoResponse monitoreoArea() {
        return reporteService.monitoreoArea();
    }

    @GetMapping("/equipos/{codigo}/historial")
    public List<IncidenciaResponse> historialEquipo(@PathVariable String codigo) {
        return incidenciaService.historialEquipo(codigo);
    }
}
