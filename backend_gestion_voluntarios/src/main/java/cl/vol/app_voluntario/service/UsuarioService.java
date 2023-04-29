package cl.vol.app_voluntario.service;

import cl.vol.app_voluntario.config.JwtService;
import cl.vol.app_voluntario.errors.*;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void createUsuario(RegisterRequest request) {
        try{
            if(!request.isVoluntario() && !request.isCoordinador()){
                throw new ApiErrorException("No ha seleccionado el tipo de usuario a crear.");
            }
            else if(request.isCoordinador() && institucionRepository.findById(request.getIdInstitucion()) == null){
                throw new ApiErrorException("La institución seleccionada no existe.");
            }
            else if(usuarioRepository.findByEmail(request.getEmail()) != null){
                throw new ApiErrorException("El correo electronico ya existe.");
            }

            //guardar usuario
            Usuario usuario = new Usuario();
            usuario.setNombre(request.getNombre());
            usuario.setApellido(request.getApellido());
            usuario.setEmail(request.getEmail());
            usuario.setPassword(passwordEncoder.encode(request.getPassword()));
            Usuario usuarioBD = usuarioRepository.save(usuario);

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
                coordinador.setInstitucion(institucionRepository.findById(request.getIdInstitucion()));
                coordinadorRepository.save(coordinador);
                roles.add(rolRepository.findById(1));
            }

           usuarioBD = usuarioRepository.saveUserRoles(usuarioBD.getId(), roles);
        }catch(Exception e){
            throw new ApiErrorException(e.getMessage());
        }
    }
    public Usuario getUsuario(int id) {
        return usuarioRepository.findById(id);
    }

    public String authentication(AuthenticationRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail());

        System.out.println("AUTH EMAIL = " + usuario);

        if(usuario == null){
            throw new AuthenticationException("El usuario no existe.");
        }

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        Rol rol = rolRepository.findById(request.getIdRol());
        Map<String, Object> extraClaims = jwtService.createExtraClaimWithIdAndRol(usuario.getId(), rol);

        return jwtService.generateToken(extraClaims, usuario);
    }
}
