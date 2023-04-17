package cl.vol.app_voluntario.service;


import cl.vol.app_voluntario.model.Coordinador;
import cl.vol.app_voluntario.model.Voluntario;
import cl.vol.app_voluntario.repository.*;
import cl.vol.app_voluntario.request.AuthenticationRequest;
import cl.vol.app_voluntario.response.AuthenticationResponse;
import cl.vol.app_voluntario.request.RegisterRequest;
import cl.vol.app_voluntario.config.JwtService;
import cl.vol.app_voluntario.errors.ExistingUserException;
import cl.vol.app_voluntario.errors.RolNotFoundException;
import cl.vol.app_voluntario.model.Rol;
import cl.vol.app_voluntario.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UsuarioRepository usuarioRepository;
    private final InstitucionRepository institucionRepository;
    private final CoordinadorRepository coordinadorRepository;
    private final VoluntarioRepository voluntarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RolRepository rolRepository;

    public AuthenticationResponse register(RegisterRequest request) {
        // buscar usuario por email
        Optional<Usuario> existingUser = usuarioRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            // usuario ya existe
            throw new ExistingUserException("Ya existe un usuario con este correo electrónico.");
        }

        List<Rol> roles = new ArrayList<>();
        Optional<Rol> rolCoordinador = rolRepository.findById(1);
        Optional<Rol> rolVoluntario = rolRepository.findById(2);


        if(!rolCoordinador.isPresent() || !rolVoluntario.isPresent()){
            throw new RolNotFoundException("No existe el rol asignado.");
        }

        Voluntario voluntario = null;
        Coordinador coordinador = null;
        if(request.isVoluntario()){
            roles.add(rolVoluntario.get());
            voluntario = new Voluntario();
        }

        if(request.isCoordinador()){
            roles.add(rolCoordinador.get());
            coordinador = new Coordinador();
        }
        else if(roles.isEmpty()){
            throw new RolNotFoundException("El rol asignado no es válido para este usuario.");
        }

        var user = Usuario.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .build();
        usuarioRepository.save(user);

        if(voluntario != null){
            voluntario.setUsuario(user);
            voluntarioRepository.save(voluntario);
        }

        if(coordinador != null){
            coordinador.setUsuario(user);
            coordinador.setInstitucion(institucionRepository.findById(request.getId_institucion()).get());
            coordinadorRepository.save(coordinador);
        }

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        //en este punto el usuario esta autenticado
        var user = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
