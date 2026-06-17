package isw.backend.service;

import isw.backend.dto.FallaDiccionarioRequest;
import isw.backend.dto.FallaDiccionarioResponse;
import isw.backend.model.FallaDiccionario;
import isw.backend.repository.FallaDiccionarioRepository;
import isw.backend.util.ResourceNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DiccionarioFallasService {
    private final FallaDiccionarioRepository fallaRepository;

    public DiccionarioFallasService(FallaDiccionarioRepository fallaRepository) {
        this.fallaRepository = fallaRepository;
    }

    @Transactional(readOnly = true)
    public List<FallaDiccionarioResponse> listar(String q) {
        List<FallaDiccionario> fallas = q == null || q.isBlank()
                ? fallaRepository.findAll()
                : fallaRepository.findByTituloContainingIgnoreCaseOrSintomasContainingIgnoreCase(q, q);
        return fallas.stream().map(FallaDiccionarioResponse::from).toList();
    }

    public FallaDiccionarioResponse crear(FallaDiccionarioRequest request) {
        FallaDiccionario falla = FallaDiccionario.builder()
                .titulo(request.titulo())
                .sintomas(request.sintomas())
                .solucionRecomendada(request.solucionRecomendada())
                .categoria(request.categoria())
                .build();
        return FallaDiccionarioResponse.from(fallaRepository.save(falla));
    }

    public FallaDiccionarioResponse actualizar(Long id, FallaDiccionarioRequest request) {
        FallaDiccionario falla = fallaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe la falla indicada"));
        falla.setTitulo(request.titulo());
        falla.setSintomas(request.sintomas());
        falla.setSolucionRecomendada(request.solucionRecomendada());
        falla.setCategoria(request.categoria());
        return FallaDiccionarioResponse.from(fallaRepository.save(falla));
    }
}
