package cl.vol.app_voluntario.controller;

import cl.vol.app_voluntario.model.Emergencia;
import cl.vol.app_voluntario.model.Tarea;
import cl.vol.app_voluntario.request.CreateEmergenciaRequest;
import cl.vol.app_voluntario.request.CreateTareaRequest;
import cl.vol.app_voluntario.service.TareaService;
import cl.vol.app_voluntario.util.ValidationUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tareas")
@RequiredArgsConstructor
public class TareaController {
    private final TareaService tareaService;

    //CREATE
    @PostMapping
    public ResponseEntity<?> createTarea(
            @Valid @RequestBody CreateTareaRequest request,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()) return new ResponseEntity<>(ValidationUtil.getValidationErrors(bindingResult), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(tareaService.createTarea(request), HttpStatus.CREATED);
    }
}
