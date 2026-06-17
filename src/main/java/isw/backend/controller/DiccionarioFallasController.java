package isw.backend.controller;

import isw.backend.dto.FallaDiccionarioRequest;
import isw.backend.dto.FallaDiccionarioResponse;
import isw.backend.service.DiccionarioFallasService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/diccionario-fallas")
public class DiccionarioFallasController {
    private final DiccionarioFallasService diccionarioFallasService;

    public DiccionarioFallasController(DiccionarioFallasService diccionarioFallasService) {
        this.diccionarioFallasService = diccionarioFallasService;
    }

    @GetMapping
    public List<FallaDiccionarioResponse> listar(@RequestParam(required = false) String q) {
        return diccionarioFallasService.listar(q);
    }

    @PostMapping
    public ResponseEntity<FallaDiccionarioResponse> crear(@Valid @RequestBody FallaDiccionarioRequest request) {
        FallaDiccionarioResponse response = diccionarioFallasService.crear(request);
        return ResponseEntity.created(URI.create("/api/v1/diccionario-fallas/" + response.id())).body(response);
    }

    @PutMapping("/{id}")
    public FallaDiccionarioResponse actualizar(
            @PathVariable Long id,
            @Valid @RequestBody FallaDiccionarioRequest request
    ) {
        return diccionarioFallasService.actualizar(id, request);
    }
}
