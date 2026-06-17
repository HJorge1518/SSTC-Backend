package isw.backend.controller;

import isw.backend.dto.AsignarIncidenciaRequest;
import isw.backend.dto.IncidenciaRequest;
import isw.backend.dto.IncidenciaResponse;
import isw.backend.dto.InformeTecnicoRequest;
import isw.backend.dto.InformeTecnicoResponse;
import isw.backend.dto.SolicitudRepuestoRequest;
import isw.backend.dto.SolicitudRepuestoResponse;
import isw.backend.service.IncidenciaService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/incidencias")
public class IncidenciaController {
    private final IncidenciaService incidenciaService;

    public IncidenciaController(IncidenciaService incidenciaService) {
        this.incidenciaService = incidenciaService;
    }

    @GetMapping
    public List<IncidenciaResponse> listar() {
        return incidenciaService.listar();
    }

    @GetMapping("/{id}")
    public IncidenciaResponse buscar(@PathVariable Long id) {
        return incidenciaService.buscar(id);
    }

    @PostMapping
    public ResponseEntity<IncidenciaResponse> registrar(@Valid @RequestBody IncidenciaRequest request) {
        IncidenciaResponse response = incidenciaService.registrar(request);
        return ResponseEntity.created(URI.create("/api/v1/incidencias/" + response.id())).body(response);
    }

    @PutMapping("/{id}/asignacion")
    public IncidenciaResponse asignar(@PathVariable Long id, @Valid @RequestBody AsignarIncidenciaRequest request) {
        return incidenciaService.asignar(id, request);
    }

    @GetMapping("/tecnico/{tecnicoId}/tareas")
    public List<IncidenciaResponse> tareasAsignadas(@PathVariable Long tecnicoId) {
        return incidenciaService.tareasAsignadas(tecnicoId);
    }

    @PostMapping("/{id}/solicitudes-repuesto")
    public ResponseEntity<SolicitudRepuestoResponse> solicitarRepuesto(
            @PathVariable Long id,
            @Valid @RequestBody SolicitudRepuestoRequest request
    ) {
        SolicitudRepuestoResponse response = incidenciaService.solicitarRepuesto(id, request);
        return ResponseEntity.created(URI.create("/api/v1/incidencias/" + id + "/solicitudes-repuesto/" + response.id()))
                .body(response);
    }

    @GetMapping("/{id}/solicitudes-repuesto")
    public List<SolicitudRepuestoResponse> listarSolicitudes(@PathVariable Long id) {
        return incidenciaService.listarSolicitudes(id);
    }

    @PostMapping("/{id}/informe")
    public ResponseEntity<InformeTecnicoResponse> registrarInforme(
            @PathVariable Long id,
            @Valid @RequestBody InformeTecnicoRequest request
    ) {
        InformeTecnicoResponse response = incidenciaService.registrarInforme(id, request);
        return ResponseEntity.created(URI.create("/api/v1/incidencias/" + id + "/informe")).body(response);
    }
}
