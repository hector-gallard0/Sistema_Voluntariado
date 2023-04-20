package cl.vol.app_voluntario.controller;

import cl.vol.app_voluntario.model.Habilidad;
import cl.vol.app_voluntario.request.CreateTareaRequest;
import cl.vol.app_voluntario.service.HabilidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class HabilidadController {
    private final HabilidadService habilidadService;

    @PostMapping("/habilidades")
    public ResponseEntity<Habilidad> createHabilidad(
            @RequestBody Habilidad request
    ){
        try{
            return new ResponseEntity<>(habilidadService.createHabilidad(request.getDescripcion()), HttpStatus.CREATED);
        }catch(Error e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
