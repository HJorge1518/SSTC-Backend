package isw.backend.repository;

import isw.backend.model.InformeTecnico;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InformeTecnicoRepository extends JpaRepository<InformeTecnico, Long> {
    Optional<InformeTecnico> findByIncidenciaId(Long incidenciaId);

    long countByIncidenciaTecnicoAsignadoIdAndFechaSolucionBetween(Long tecnicoId, LocalDateTime desde, LocalDateTime hasta);
}
