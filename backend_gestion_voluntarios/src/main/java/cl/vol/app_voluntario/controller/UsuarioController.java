package cl.vol.app_voluntario.controller;

import cl.vol.app_voluntario.model.Usuario;
import cl.vol.app_voluntario.request.AuthenticationRequest;
import cl.vol.app_voluntario.request.RegisterRequest;
import cl.vol.app_voluntario.response.AuthenticationResponse;
import cl.vol.app_voluntario.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    //CREATE o REGISTRO
    @PostMapping("/usuarios")
    public AuthenticationResponse createUsuario(
            @RequestBody RegisterRequest request
    ){
        return usuarioService.createUsuario(request);
    }

    //LOGIN
    @PostMapping("/login")
    public AuthenticationResponse authentication(
            @RequestBody AuthenticationRequest request
            ){
        return usuarioService.authentication(request);
    }

    //READ
    @GetMapping("/usuarios")
    public List<Usuario> getUsuarios(){
        return usuarioService.getUsuarios();
    }

    //READ 1
    @GetMapping("/{id}")
    public Usuario getUsuario(@PathVariable int id){
        return usuarioService.getUsuario(id);
    }


}
