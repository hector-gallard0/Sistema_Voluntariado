package cl.vol.app_voluntario.service;

import cl.vol.app_voluntario.model.Usuario;
import cl.vol.app_voluntario.repository.UsuarioRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public List<Usuario> getUsuarios(){
        return usuarioRepository.findAll();
    }
}
