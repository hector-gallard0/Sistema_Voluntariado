package cl.vol.app_voluntario.service;

import cl.vol.app_voluntario.errors.ApiErrorException;
import cl.vol.app_voluntario.model.Habilidad;
import cl.vol.app_voluntario.model.Usuario;
import cl.vol.app_voluntario.repository.EmergenciaRepository;
import cl.vol.app_voluntario.repository.TareaRepository;
import cl.vol.app_voluntario.repository.UsuarioRepository;
import cl.vol.app_voluntario.repository.VoluntarioRepository;
import cl.vol.app_voluntario.request.CreateVolTareaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoluntarioService {
    @Autowired
    private final VoluntarioRepository voluntarioRepository;

    @Autowired
    private final UsuarioRepository usuarioRepository;

    @Autowired
    private final TareaRepository tareaRepository;

    @Autowired
    private final EmergenciaRepository emergenciaRepository;

    public Habilidad addHabilidadVoluntario(Integer idVoluntario, Integer idHabilidad){
        return voluntarioRepository.saveVolHabilidad(idVoluntario, idHabilidad);
    }

    public List<Usuario> getVoluntarios() { return usuarioRepository.findAllByRoleId(2); }

    public void addTareaVoluntario(Integer idVoluntario, Integer idTarea, CreateVolTareaRequest request) {
        try{
            if(tareaRepository.findById(idTarea) == null) throw new ApiErrorException("La tarea no existe.");
            if(voluntarioRepository.findById(idVoluntario) == null) throw new ApiErrorException("El voluntario no existe.");

            voluntarioRepository.saveVolTarea(idVoluntario, idTarea, request);

        }catch (Exception e){
            throw new ApiErrorException("No se pudo asignar la tarea al voluntario " + e.getMessage());
        }
    }
}
