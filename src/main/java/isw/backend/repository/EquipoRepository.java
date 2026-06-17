package isw.backend.repository;

import isw.backend.model.Equipo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    Optional<Equipo> findByCodigoIgnoreCase(String codigo);
}
