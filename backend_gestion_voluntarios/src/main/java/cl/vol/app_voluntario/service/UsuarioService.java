package cl.vol.app_voluntario.service;

import cl.vol.app_voluntario.config.JwtService;
import cl.vol.app_voluntario.errors.AuthenticationException;
import cl.vol.app_voluntario.model.Coordinador;
import cl.vol.app_voluntario.model.Rol;
import cl.vol.app_voluntario.model.Usuario;
import cl.vol.app_voluntario.model.Voluntario;
import cl.vol.app_voluntario.repository.*;
import cl.vol.app_voluntario.request.AuthenticationRequest;
import cl.vol.app_voluntario.request.RegisterRequest;
import cl.vol.app_voluntario.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final InstitucionRepository institucionRepository;
    private final CoordinadorRepository coordinadorRepository;
    private final VoluntarioRepository voluntarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    public List<Usuario> getUsuarios(){
        return usuarioRepository.findAll();
    }

    public AuthenticationResponse createUsuario(RegisterRequest request) {
        try{
            if(!request.isVoluntario() && !request.isCoordinador()){
                throw new AuthenticationException("No hay roles para asignar a este usuario.");
            }
            else if(institucionRepository.findById(request.getId_institucion()) == null){
                throw new AuthenticationException("No hay una institucion para asignar a este usuario.");
            }
            else if(usuarioRepository.findByEmail(request.getEmail()) != null){
                throw new AuthenticationException("El correo electronico ya existe.");
            }


            //guardar usuario
            Usuario usuario = new Usuario();
            usuario.setNombre(request.getNombre());
            usuario.setApellido(request.getApellido());
            usuario.setEmail(request.getEmail());
            usuario.setPassword(passwordEncoder.encode(request.getPassword()));
            usuarioRepository.save(usuario);

            Usuario usuarioBD = usuarioRepository.findByEmail(usuario.getEmail());

            //guardar roles
            List<Rol> roles = new ArrayList<>();

            if(request.isVoluntario()){
                Voluntario voluntario = new Voluntario();
                voluntario.setUsuario(usuarioBD);
                voluntario.setHabilidades(new ArrayList<>());
                voluntario.setTareas(new ArrayList<>());
                voluntarioRepository.save(voluntario);
                roles.add(rolRepository.findById(2));
            }

            if(request.isCoordinador()){
                Coordinador coordinador = new Coordinador();
                coordinador.setUsuario(usuarioBD);
                coordinador.setInstitucion(institucionRepository.findById(request.getId_institucion()));
                coordinadorRepository.save(coordinador);
                roles.add(rolRepository.findById(1));
            }

            usuarioRepository.saveUserRoles(usuarioRepository.findByEmail(usuario.getEmail()).getId(), roles);

            var jwtToken = jwtService.generateToken(usuarioBD);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        }catch(Exception e){
            throw new AuthenticationException("Ha ocurrido un error en el registro del usuario.\n" + e.getMessage());
        }
    }
    public Usuario getUsuario(int id) {
        return usuarioRepository.findById(id);
    }

    public AuthenticationResponse authentication(AuthenticationRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail());

        if(usuario == null){
            throw new AuthenticationException("El usuario no existe.");
        }

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var jwtToken = jwtService.generateToken(usuario);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
