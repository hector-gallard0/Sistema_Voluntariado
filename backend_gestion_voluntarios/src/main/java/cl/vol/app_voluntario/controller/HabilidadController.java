package cl.vol.app_voluntario.controller;

import cl.vol.app_voluntario.model.Habilidad;
import cl.vol.app_voluntario.request.CreateTareaRequest;
import cl.vol.app_voluntario.service.HabilidadService;
import cl.vol.app_voluntario.util.ValidationUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/habilidades")
@RequiredArgsConstructor
public class HabilidadController {
    private final HabilidadService habilidadService;

    //CREATE HABILIDAD
    public ResponseEntity<?> createHabilidad(
            @Valid @RequestBody Habilidad request,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()) return new ResponseEntity<>(ValidationUtil.getValidationErrors(bindingResult), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(habilidadService.createHabilidad(request.getDescripcion()), HttpStatus.CREATED);
    }
}
