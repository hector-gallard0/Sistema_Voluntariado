package cl.vol.app_voluntario.controller;

import cl.vol.app_voluntario.model.Usuario;
import cl.vol.app_voluntario.request.AuthenticationRequest;
import cl.vol.app_voluntario.request.RegisterRequest;
import cl.vol.app_voluntario.response.AuthenticationResponse;
import cl.vol.app_voluntario.service.UsuarioService;
import cl.vol.app_voluntario.util.ValidationUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    //CREATE o REGISTRO
    @PostMapping("/register")
    public ResponseEntity<?> createUsuario(
            @Valid @RequestBody RegisterRequest request,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()) return new ResponseEntity<>(ValidationUtil.getValidationErrors(bindingResult), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(usuarioService.createUsuario(request), HttpStatus.CREATED);
    }

    //LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> authentication(
            @Valid @RequestBody AuthenticationRequest request,
            BindingResult bindingResult
            ){
        if(bindingResult.hasErrors()) return new ResponseEntity<>(ValidationUtil.getValidationErrors(bindingResult), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(usuarioService.authentication(request), HttpStatus.OK);
    }

    //READ
    @GetMapping
    public ResponseEntity<?> getUsuarios(){
        return new ResponseEntity<>(usuarioService.getUsuarios(), HttpStatus.FOUND);
    }

    //READ 1
    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuario(@PathVariable Integer id){
        return new ResponseEntity<>(usuarioService.getUsuario(id), HttpStatus.FOUND);
    }
}
