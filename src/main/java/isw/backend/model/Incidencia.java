package isw.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "incidencias")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Incidencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "equipo_id", nullable = false)
    private Equipo equipo;

    @Column(nullable = false, length = 800)
    private String problema;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CanalRegistro canalRegistro;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "registrado_por_id", nullable = false)
    private MiembroArea registradoPor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tecnico_id")
    private Tecnico tecnicoAsignado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoIncidencia estado;

    @Column(length = 800)
    private String diagnosticoInicial;

    @PrePersist
    void prePersist() {
        if (fechaRegistro == null) {
            fechaRegistro = LocalDateTime.now();
        }
        if (estado == null) {
            estado = EstadoIncidencia.PENDIENTE;
        }
    }
}
