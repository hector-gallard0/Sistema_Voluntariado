package cl.vol.app_voluntario.controller;

import cl.vol.app_voluntario.model.Habilidad;
import cl.vol.app_voluntario.model.Institucion;
import cl.vol.app_voluntario.request.CreateVolTareaRequest;
import cl.vol.app_voluntario.response.ApiResponse;
import cl.vol.app_voluntario.service.VoluntarioService;
import cl.vol.app_voluntario.util.ValidationUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
public class VoluntarioController {
    private final VoluntarioService voluntarioService;

    @GetMapping("/voluntarios")
    public ResponseEntity<?> getVoluntarios(){
        return new ResponseEntity<>(voluntarioService.getVoluntarios(), HttpStatus.OK);
    }

    @PutMapping("/voluntarios/{idVoluntario}/habilidades/{idHabilidad}")
    public ResponseEntity<?> addHabilidadVoluntario(
            @Valid @NotNull @PathVariable Integer idVoluntario,
            @Valid @NotNull @PathVariable Integer idHabilidad,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()) return new ResponseEntity<>(ValidationUtil.getValidationErrors(bindingResult), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(voluntarioService.addHabilidadVoluntario(idVoluntario, idHabilidad), HttpStatus.OK);
    }

    @PutMapping("/voluntarios/{idVoluntario}/tareas/{idTarea}")
    public ResponseEntity<?> addTareaVoluntario(
            @Valid @NotNull @PathVariable Integer idVoluntario,
            @Valid @NotNull @PathVariable Integer idTarea,
            @Valid @RequestBody CreateVolTareaRequest request,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()) return new ResponseEntity<>(ValidationUtil.getValidationErrors(bindingResult), HttpStatus.BAD_REQUEST);
        voluntarioService.addTareaVoluntario(idVoluntario, idTarea, request);
        return new ResponseEntity<>("Exito", HttpStatus.OK);
    }
}
