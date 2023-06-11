package cl.vol.app_voluntario.service;

import cl.vol.app_voluntario.model.Habilidad;
import cl.vol.app_voluntario.model.Usuario;
import cl.vol.app_voluntario.model.Voluntario;
import cl.vol.app_voluntario.repository.UsuarioRepository;
import cl.vol.app_voluntario.repository.VoluntarioRepository;
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

    public Habilidad addHabilidadVoluntario(Integer idVoluntario, Integer idHabilidad){
        return voluntarioRepository.saveVolHabilidad(idVoluntario, idHabilidad);
    }

    public List<Usuario> getVoluntarios() { return usuarioRepository.findAllByRoleId(2); }
}
