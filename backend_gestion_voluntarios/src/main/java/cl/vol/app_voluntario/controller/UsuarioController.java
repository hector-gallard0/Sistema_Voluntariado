package cl.vol.app_voluntario.controller;

import cl.vol.app_voluntario.request.AuthenticationRequest;
import cl.vol.app_voluntario.request.RegisterRequest;
import cl.vol.app_voluntario.response.ApiResponse;
import cl.vol.app_voluntario.service.UsuarioService;
import cl.vol.app_voluntario.util.ValidationUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    //CREATE o REGISTRO
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> createUsuario(
            @Valid @RequestBody RegisterRequest request,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>
                (new ApiResponse().builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .messages(ValidationUtil.getValidationErrors(bindingResult))
                    .build(),
                    HttpStatus.BAD_REQUEST);
        };

        usuarioService.createUsuario(request);

        return new ResponseEntity<>
                (new ApiResponse().builder()
                        .status(HttpStatus.OK.value())
                        .messages(new HashMap<>().put("exito", "Usuario registrado con éxito."))
                        .build(),
                        HttpStatus.OK);
    }

    //LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> authentication(
            @Valid @RequestBody AuthenticationRequest request,
            BindingResult bindingResult
            ){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>
                    (new ApiResponse().builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .messages(ValidationUtil.getValidationErrors(bindingResult))
                            .build(),
                            HttpStatus.BAD_REQUEST);
        };
        System.out.println("Solicitando login");
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("token",  usuarioService.authentication(request));

        return new ResponseEntity<>
                (new ApiResponse().builder()
                        .status(HttpStatus.OK.value())
                        .messages("Éxito")
                        .data(dataMap)
                        .build(),
                        HttpStatus.OK);
    }

    //READ
    @GetMapping("/usuarios")
    public ResponseEntity<?> getUsuarios(){
        System.out.println("get usuarios");
        return new ResponseEntity<>(usuarioService.getUsuarios(), HttpStatus.FOUND);
    }

    //READ 1
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<?> getUsuario(@PathVariable Integer id){
        return new ResponseEntity<>(usuarioService.getUsuario(id), HttpStatus.FOUND);
    }
}
