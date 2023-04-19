package cl.vol.app_voluntario.controller;

import cl.vol.app_voluntario.model.Usuario;
import cl.vol.app_voluntario.request.RegisterRequest;
import cl.vol.app_voluntario.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    //CREATE
    @PostMapping("/")
    public boolean createUsuario(
            @RequestBody RegisterRequest request
    ){
        return usuarioService.createUsuario(request);
    }

    //READ
    @GetMapping("/")
    public List<Usuario> getUsuarios(){
        return usuarioService.getUsuarios();
    }

    //READ 1
    @GetMapping("/{id}")
    public Usuario getUsuario(@PathVariable int id){
        return usuarioService.getUsuario(id);
    }


}
