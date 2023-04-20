package cl.vol.app_voluntario.controller;

import cl.vol.app_voluntario.model.Emergencia;
import cl.vol.app_voluntario.model.Tarea;
import cl.vol.app_voluntario.request.CreateEmergenciaRequest;
import cl.vol.app_voluntario.request.CreateTareaRequest;
import cl.vol.app_voluntario.service.TareaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TareaController {
    private final TareaService tareaService;

    //CREATE
    @PostMapping("/tareas")
    public ResponseEntity<Tarea> createTarea(
            @RequestBody CreateTareaRequest request
    ){
        return new ResponseEntity<>(tareaService.createTarea(request), HttpStatus.CREATED);
    }
}
