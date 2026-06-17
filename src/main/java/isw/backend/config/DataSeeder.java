package isw.backend.config;

import isw.backend.model.CanalRegistro;
import isw.backend.model.Equipo;
import isw.backend.model.EstadoIncidencia;
import isw.backend.model.FallaDiccionario;
import isw.backend.model.Incidencia;
import isw.backend.model.MiembroArea;
import isw.backend.model.RolMiembro;
import isw.backend.model.Tecnico;
import isw.backend.model.Usuario;
import isw.backend.repository.EquipoRepository;
import isw.backend.repository.FallaDiccionarioRepository;
import isw.backend.repository.IncidenciaRepository;
import isw.backend.repository.MiembroAreaRepository;
import isw.backend.repository.TecnicoRepository;
import isw.backend.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {
    private final UsuarioRepository usuarioRepository;
    private final EquipoRepository equipoRepository;
    private final TecnicoRepository tecnicoRepository;
    private final MiembroAreaRepository miembroAreaRepository;
    private final IncidenciaRepository incidenciaRepository;
    private final FallaDiccionarioRepository fallaDiccionarioRepository;

    public DataSeeder(
            UsuarioRepository usuarioRepository,
            EquipoRepository equipoRepository,
            TecnicoRepository tecnicoRepository,
            MiembroAreaRepository miembroAreaRepository,
            IncidenciaRepository incidenciaRepository,
            FallaDiccionarioRepository fallaDiccionarioRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.equipoRepository = equipoRepository;
        this.tecnicoRepository = tecnicoRepository;
        this.miembroAreaRepository = miembroAreaRepository;
        this.incidenciaRepository = incidenciaRepository;
        this.fallaDiccionarioRepository = fallaDiccionarioRepository;
    }

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() > 0) {
            return;
        }

        Usuario ana = usuarioRepository.save(Usuario.builder()
                .nombre("Ana Torres")
                .email("ana.torres@softcorp.com")
                .area("Contabilidad")
                .telefono("555-0101")
                .build());
        Usuario luis = usuarioRepository.save(Usuario.builder()
                .nombre("Luis Ramos")
                .email("luis.ramos@softcorp.com")
                .area("Ventas")
                .telefono("555-0102")
                .build());

        Equipo pc001 = equipoRepository.save(Equipo.builder()
                .codigo("PC-001")
                .tipo("PC")
                .marcaModelo("Dell OptiPlex 7090")
                .ubicacion("Piso 2 - Contabilidad")
                .responsable(ana)
                .build());
        equipoRepository.save(Equipo.builder()
                .codigo("PC-002")
                .tipo("Laptop")
                .marcaModelo("Lenovo ThinkPad E14")
                .ubicacion("Piso 3 - Ventas")
                .responsable(luis)
                .build());

        Tecnico carlos = tecnicoRepository.save(Tecnico.builder()
                .nombre("Carlos Medina")
                .email("carlos.medina@softcorp.com")
                .limiteAtencion(3)
                .activo(true)
                .build());
        tecnicoRepository.save(Tecnico.builder()
                .nombre("Mariana Flores")
                .email("mariana.flores@softcorp.com")
                .limiteAtencion(2)
                .activo(true)
                .build());

        MiembroArea jefe = miembroAreaRepository.save(MiembroArea.builder()
                .nombre("Jorge Salazar")
                .rol(RolMiembro.JEFE_AREA)
                .build());
        miembroAreaRepository.save(MiembroArea.builder()
                .nombre("Patricia Vega")
                .rol(RolMiembro.PERSONAL_SISTEMAS)
                .build());

        incidenciaRepository.save(Incidencia.builder()
                .equipo(pc001)
                .problema("El equipo no enciende despues de una variacion electrica")
                .canalRegistro(CanalRegistro.TELEFONO)
                .registradoPor(jefe)
                .tecnicoAsignado(carlos)
                .estado(EstadoIncidencia.PENDIENTE)
                .diagnosticoInicial("Posible falla de fuente de poder")
                .build());

        fallaDiccionarioRepository.save(FallaDiccionario.builder()
                .titulo("Equipo no enciende")
                .sintomas("No hay luces, ventiladores ni senal de energia")
                .solucionRecomendada("Verificar cable, estabilizador y fuente de poder; reemplazar fuente si no entrega voltaje")
                .categoria("Hardware")
                .build());
        fallaDiccionarioRepository.save(FallaDiccionario.builder()
                .titulo("Lentitud al iniciar sesion")
                .sintomas("El escritorio demora varios minutos en cargar")
                .solucionRecomendada("Revisar programas de inicio, espacio en disco y estado SMART del almacenamiento")
                .categoria("Sistema operativo")
                .build());
    }
}
