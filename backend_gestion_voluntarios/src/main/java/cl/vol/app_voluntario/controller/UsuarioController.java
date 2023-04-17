package cl.vol.app_voluntario.controller;

import cl.vol.app_voluntario.model.Usuario;
import cl.vol.app_voluntario.request.RegisterRequest;
import cl.vol.app_voluntario.response.AuthenticationResponse;
import cl.vol.app_voluntario.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public List<Usuario> getUsuarios(){
        return usuarioService.getUsuarios();
    }

}
