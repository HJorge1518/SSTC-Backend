package isw.backend.repository;

import isw.backend.model.EstadoIncidencia;
import isw.backend.model.Incidencia;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidenciaRepository extends JpaRepository<Incidencia, Long> {
    long countByEstado(EstadoIncidencia estado);

    long countByTecnicoAsignadoIdAndEstado(Long tecnicoId, EstadoIncidencia estado);

    long countByTecnicoAsignadoIdAndFechaRegistroBetween(Long tecnicoId, LocalDateTime desde, LocalDateTime hasta);

    List<Incidencia> findByTecnicoAsignadoIdOrderByFechaRegistroDesc(Long tecnicoId);

    List<Incidencia> findByEquipoCodigoIgnoreCaseOrderByFechaRegistroDesc(String codigo);
}
