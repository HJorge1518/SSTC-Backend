package isw.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "solicitudes_repuesto")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudRepuesto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "incidencia_id", nullable = false)
    private Incidencia incidencia;

    @Column(nullable = false, length = 160)
    private String repuesto;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private LocalDateTime fechaSolicitud;

    @Column(nullable = false, length = 30)
    private String estadoLogistica;

    @PrePersist
    void prePersist() {
        if (fechaSolicitud == null) {
            fechaSolicitud = LocalDateTime.now();
        }
        if (estadoLogistica == null || estadoLogistica.isBlank()) {
            estadoLogistica = "SOLICITADO";
        }
    }
}
