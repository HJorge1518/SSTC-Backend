package isw.backend.repository;

import isw.backend.model.Tecnico;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TecnicoRepository extends JpaRepository<Tecnico, Long> {
    List<Tecnico> findByActivoTrue();
}
