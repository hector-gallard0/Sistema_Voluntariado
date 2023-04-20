package cl.vol.app_voluntario.service;

import cl.vol.app_voluntario.model.Habilidad;
import cl.vol.app_voluntario.repository.VoluntarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoluntarioService {
    @Autowired
    private final VoluntarioRepository voluntarioRepository;

    public Habilidad addHabilidadVoluntario(Integer idVoluntario, Integer idHabilidad){
        return voluntarioRepository.saveVolHabilidad(idVoluntario, idHabilidad);
    }
}
