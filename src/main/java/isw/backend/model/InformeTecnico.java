package isw.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "informes_tecnicos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InformeTecnico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "incidencia_id", nullable = false, unique = true)
    private Incidencia incidencia;

    @Column(nullable = false, length = 1200)
    private String descripcionSolucion;

    @Column(nullable = false)
    private Boolean requirioRepuesto;

    @Column(nullable = false)
    private LocalDateTime fechaSolucion;

    @PrePersist
    void prePersist() {
        if (fechaSolucion == null) {
            fechaSolucion = LocalDateTime.now();
        }
    }
}
