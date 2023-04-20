package cl.vol.app_voluntario.service;

import cl.vol.app_voluntario.model.Habilidad;
import cl.vol.app_voluntario.repository.HabilidadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HabilidadService {
    private final HabilidadRepository habilidadRepository;

    public Habilidad createHabilidad(String descripcion){
        Habilidad habilidad = new Habilidad();
        habilidad.setDescripcion(descripcion);
        return habilidadRepository.save(habilidad);
    }

}
