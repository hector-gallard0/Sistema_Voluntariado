package cl.vol.app_voluntario.controller;

import cl.vol.app_voluntario.service.EstadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/estados")
@RequiredArgsConstructor
public class EstadoController {
    private final EstadoService estadoService;

    @GetMapping
    public ResponseEntity<?> getEstados(){
        return new ResponseEntity<>(estadoService.getEstados(), HttpStatus.OK);
    };
}
