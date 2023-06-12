package cl.vol.app_voluntario.controller;

import cl.vol.app_voluntario.response.ApiResponse;
import cl.vol.app_voluntario.service.CoordinadorService;
import cl.vol.app_voluntario.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CoordinadorController {
    private final CoordinadorService coordinadorService;
    @GetMapping("/coordinadores")
    public ResponseEntity<?> getCoordinadores(){
        return new ResponseEntity<>(coordinadorService.getCoordinadores(), HttpStatus.OK);
    }

}
