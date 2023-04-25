package cl.vol.app_voluntario.service;

import cl.vol.app_voluntario.model.Institucion;
import cl.vol.app_voluntario.repository.InstitucionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstitucionService {
    private final InstitucionRepository institucionRepository;

    public List<Institucion> getInstituciones(){
        return institucionRepository.findAll();
    }
}
