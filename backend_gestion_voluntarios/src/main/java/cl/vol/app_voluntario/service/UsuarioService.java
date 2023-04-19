package cl.vol.app_voluntario.service;

import cl.vol.app_voluntario.model.Coordinador;
import cl.vol.app_voluntario.model.Usuario;
import cl.vol.app_voluntario.model.Voluntario;
import cl.vol.app_voluntario.repository.UsuarioRepository;
import cl.vol.app_voluntario.request.RegisterRequest;
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

    public boolean createUsuario(RegisterRequest request) {
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(request.getPassword());

        if(request.isVoluntario()){
            Voluntario voluntario = new Voluntario();
            usuario.setVoluntario(voluntario);
        }

        if(request.isCoordinador()){
            Coordinador coordinador = new Coordinador();
            usuario.setCoordinador(coordinador);
        }

        return usuarioRepository.save(usuario);
    }

    public Usuario getUsuario(int id) {
        return usuarioRepository.findById(id);
    }
}
