package isw.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "diccionario_fallas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FallaDiccionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 160)
    private String titulo;

    @Column(nullable = false, length = 800)
    private String sintomas;

    @Column(nullable = false, length = 1200)
    private String solucionRecomendada;

    @Column(length = 80)
    private String categoria;
}
