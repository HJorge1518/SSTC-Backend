package isw.backend.repository;

import isw.backend.model.FallaDiccionario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FallaDiccionarioRepository extends JpaRepository<FallaDiccionario, Long> {
    List<FallaDiccionario> findByTituloContainingIgnoreCaseOrSintomasContainingIgnoreCase(String titulo, String sintomas);
}
