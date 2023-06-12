package cl.vol.app_voluntario.service;

import cl.vol.app_voluntario.config.JwtService;
import cl.vol.app_voluntario.errors.*;
import cl.vol.app_voluntario.model.*;
import cl.vol.app_voluntario.repository.*;
import cl.vol.app_voluntario.request.AuthenticationRequest;
import cl.vol.app_voluntario.request.RegisterRequest;
import cl.vol.app_voluntario.request.UpdateUsuarioRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private final TareaRepository tareaRepository;
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

            Usuario usuario = new Usuario();
            usuario.setNombre(request.getNombre());
            usuario.setApellido(request.getApellido());
            usuario.setEmail(request.getEmail());
            usuario.setPassword(passwordEncoder.encode(request.getPassword()));
            usuario.setLatit(request.getLatit());
            usuario.setLongit(request.getLongit());
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
            throw new ApiErrorException("Error al crear usuario " + e.getMessage());
        }
    }
    public Usuario getUsuario(int id) {
        return usuarioRepository.findById(id);
    }
    public List<Usuario> getAllVoluntarios(){ return usuarioRepository.findAllByRoleId(2);}
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

    public void deleteUsuario(Integer id) {
        try{
            Usuario usuario = usuarioRepository.findById(id);
            if(usuario == null) throw new ApiErrorException("El usuario no existe");

            if(usuario.getCoordinador() != null) coordinadorRepository.delete(usuario.getCoordinador().getId());
            if(usuario.getVoluntario() != null){
                tareaRepository.deleteRankingByVoluntarioId(usuario.getVoluntario().getId());
                voluntarioRepository.deleteVolHabilidadByVoluntarioId(usuario.getVoluntario().getId());
                voluntarioRepository.delete(usuario.getVoluntario().getId());
            }
            usuarioRepository.delete(id);

        }catch(Exception e){
            throw new ApiErrorException("El usuario no ha podido ser eliminado " + e.getMessage());
        }
    }

//    public void updateUsuario(Integer id, UpdateUsuarioRequest newUsuario) {
//        try{
//            Usuario usuario = usuarioRepository.findById(id);
//            if(usuario == null) throw new ApiErrorException("El usuario no existe");
//            if(newUsuario.getNombre() != null){
//                usuario.setNombre(newUsuario.getNombre());
//            }
//            if(newUsuario.getApellido() != null){
//                usuario.setApellido(newUsuario.getApellido());
//            }
//            if(newUsuario.getEmail() != null){
//                usuario.setEmail(newUsuario.getEmail());
//            }
//            if(newUsuario.getPassword() != null){
//                usuario.setPassword(passwordEncoder.encode(newUsuario.getPassword()));
//            }
//            if(newUsuario.getLatit() != null && newUsuario.getLongit() != null){
//                usuario.setLatit(newUsuario.getLatit());
//                usuario.setLongit(newUsuario.getLongit());
//            }
//            usuarioRepository.set(usuario);
//        }catch(Exception e){
//            throw new ApiErrorException("No se pudo actualizar el usuario " + e.getMessage());
//        }
//    }
}
