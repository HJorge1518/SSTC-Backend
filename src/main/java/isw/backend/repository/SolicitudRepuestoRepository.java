package isw.backend.repository;

import isw.backend.model.SolicitudRepuesto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitudRepuestoRepository extends JpaRepository<SolicitudRepuesto, Long> {
    List<SolicitudRepuesto> findByIncidenciaId(Long incidenciaId);
}
