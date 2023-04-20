package cl.vol.app_voluntario.service;

import cl.vol.app_voluntario.model.Habilidad;
import cl.vol.app_voluntario.repository.VoluntarioRepository;
import cl.vol.app_voluntario.response.AddHabilidadVoluntarioResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoluntarioService {
    @Autowired
    private final VoluntarioRepository voluntarioRepository;

    public AddHabilidadVoluntarioResponse addHabilidadVoluntario(Integer idVoluntario, Integer idHabilidad){
        Habilidad habilidad = voluntarioRepository.saveVolHabilidad(idVoluntario, idHabilidad);
        AddHabilidadVoluntarioResponse response = new AddHabilidadVoluntarioResponse();

        if(habilidad == null){
            response.setMessage("Ocurrió un error al asignar la habilidad al voluntario");
            return response;
        }

        response.setHabilidad(habilidad);
        response.setMessage("Habilidad asignada al voluntario con éxito.");

        return response;
    }
}
