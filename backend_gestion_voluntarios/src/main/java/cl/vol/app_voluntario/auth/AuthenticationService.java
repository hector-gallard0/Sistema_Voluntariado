package cl.vol.app_voluntario.auth;


import cl.vol.app_voluntario.config.JwtService;
import cl.vol.app_voluntario.errors.ExistingUserException;
import cl.vol.app_voluntario.usuario.Rol;
import cl.vol.app_voluntario.usuario.RolRepository;
import cl.vol.app_voluntario.usuario.Usuario;
import cl.vol.app_voluntario.usuario.UsuarioRepository;
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

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RolRepository rolRepository;

    public AuthenticationResponse register(RegisterRequest request) {
        // buscar usuario por email
        Optional<Usuario> existingUser = repository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            // usuario ya existe
            throw new ExistingUserException("Ya existe un usuario con este correo electrónico.");
        }

        List<Rol> roles = new ArrayList<>();

        if(request.isCoordinador() && request.isVoluntario()){
                roles.add(rolRepository.findById(1).get());
                roles.add(rolRepository.findById(2).get());
        }
        else if(request.isVoluntario()){
            roles.add(rolRepository.findById(1).get());
        }
        else if(request.isCoordinador()){
            roles.add(rolRepository.findById(2).get());
        }
        else{

        }

        var user = Usuario.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .build();
        repository.save(user);
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
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
