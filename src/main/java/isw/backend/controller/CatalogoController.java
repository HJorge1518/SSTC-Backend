package isw.backend.controller;

import isw.backend.dto.EquipoRequest;
import isw.backend.dto.EquipoResponse;
import isw.backend.dto.MiembroAreaRequest;
import isw.backend.dto.MiembroAreaResponse;
import isw.backend.dto.TecnicoRequest;
import isw.backend.dto.TecnicoResponse;
import isw.backend.dto.UsuarioRequest;
import isw.backend.dto.UsuarioResponse;
import isw.backend.service.EquipoService;
import isw.backend.service.MiembroAreaService;
import isw.backend.service.TecnicoService;
import isw.backend.service.UsuarioService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class CatalogoController {
    private final UsuarioService usuarioService;
    private final EquipoService equipoService;
    private final TecnicoService tecnicoService;
    private final MiembroAreaService miembroAreaService;

    public CatalogoController(
            UsuarioService usuarioService,
            EquipoService equipoService,
            TecnicoService tecnicoService,
            MiembroAreaService miembroAreaService
    ) {
        this.usuarioService = usuarioService;
        this.equipoService = equipoService;
        this.tecnicoService = tecnicoService;
        this.miembroAreaService = miembroAreaService;
    }

    @GetMapping("/usuarios")
    public List<UsuarioResponse> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @PostMapping("/usuarios")
    public ResponseEntity<UsuarioResponse> crearUsuario(@Valid @RequestBody UsuarioRequest request) {
        UsuarioResponse response = usuarioService.crearUsuario(request);
        return ResponseEntity.created(URI.create("/api/v1/usuarios/" + response.id())).body(response);
    }

    @GetMapping("/equipos")
    public List<EquipoResponse> listarEquipos() {
        return equipoService.listarEquipos();
    }

    @PostMapping("/equipos")
    public ResponseEntity<EquipoResponse> crearEquipo(@Valid @RequestBody EquipoRequest request) {
        EquipoResponse response = equipoService.crearEquipo(request);
        return ResponseEntity.created(URI.create("/api/v1/equipos/" + response.id())).body(response);
    }

    @GetMapping("/tecnicos")
    public List<TecnicoResponse> listarTecnicos() {
        return tecnicoService.listarTecnicos();
    }

    @GetMapping("/tecnicos/disponibles")
    public List<TecnicoResponse> listarTecnicosDisponibles() {
        return tecnicoService.listarTecnicosDisponibles();
    }

    @PostMapping("/tecnicos")
    public ResponseEntity<TecnicoResponse> crearTecnico(@Valid @RequestBody TecnicoRequest request) {
        TecnicoResponse response = tecnicoService.crearTecnico(request);
        return ResponseEntity.created(URI.create("/api/v1/tecnicos/" + response.id())).body(response);
    }

    @GetMapping("/miembros-area")
    public List<MiembroAreaResponse> listarMiembrosArea() {
        return miembroAreaService.listarMiembrosArea();
    }

    @PostMapping("/miembros-area")
    public ResponseEntity<MiembroAreaResponse> crearMiembroArea(@Valid @RequestBody MiembroAreaRequest request) {
        MiembroAreaResponse response = miembroAreaService.crearMiembroArea(request);
        return ResponseEntity.created(URI.create("/api/v1/miembros-area/" + response.id())).body(response);
    }
}
