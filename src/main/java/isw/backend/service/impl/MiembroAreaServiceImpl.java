package isw.backend.service.impl;

import isw.backend.dto.MiembroAreaRequest;
import isw.backend.dto.MiembroAreaResponse;
import isw.backend.model.MiembroArea;
import isw.backend.repository.MiembroAreaRepository;
import isw.backend.service.MiembroAreaService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MiembroAreaServiceImpl implements MiembroAreaService {
    private final MiembroAreaRepository miembroAreaRepository;

    public MiembroAreaServiceImpl(MiembroAreaRepository miembroAreaRepository) {
        this.miembroAreaRepository = miembroAreaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MiembroAreaResponse> listarMiembrosArea() {
        return miembroAreaRepository.findAll().stream().map(MiembroAreaResponse::from).toList();
    }

    @Override
    public MiembroAreaResponse crearMiembroArea(MiembroAreaRequest request) {
        MiembroArea miembro = MiembroArea.builder()
                .nombre(request.nombre())
                .rol(request.rol())
                .build();
        return MiembroAreaResponse.from(miembroAreaRepository.save(miembro));
    }
}
