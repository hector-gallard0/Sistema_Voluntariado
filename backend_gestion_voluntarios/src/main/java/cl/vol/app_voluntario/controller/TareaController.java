package cl.vol.app_voluntario.controller;

import cl.vol.app_voluntario.model.Emergencia;
import cl.vol.app_voluntario.model.Tarea;
import cl.vol.app_voluntario.request.CreateEmergenciaRequest;
import cl.vol.app_voluntario.request.CreateTareaRequest;
import cl.vol.app_voluntario.response.ApiResponse;
import cl.vol.app_voluntario.service.TareaService;
import cl.vol.app_voluntario.util.ValidationUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TareaController {
    private final TareaService tareaService;

    //READ
    @GetMapping("/tareas")
    public ResponseEntity<?> getTareas(){
        return new ResponseEntity<>(tareaService.getTareas(), HttpStatus.OK);
    };

    //READ ONE
    @GetMapping("/tareas/{id}")
    public ResponseEntity<?> getTarea(@PathVariable Integer id){
        return new ResponseEntity<>(tareaService.getTarea(id), HttpStatus.FOUND);
    };

    //CREATE
    @PostMapping("/tareas")
    public ResponseEntity<?> createTarea(
            @Valid @RequestBody CreateTareaRequest request,
            BindingResult bindingResult
    ){
        System.out.println(request);
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>
                    (new ApiResponse().builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .messages(ValidationUtil.getValidationErrors(bindingResult))
                            .build(),
                            HttpStatus.BAD_REQUEST);
        };

        System.out.println(request);
        tareaService.createTarea(request);
        Map<String, String> messages = new HashMap<>();
        messages.put("exito", "Tarea creada con éxito.");

        return new ResponseEntity<>
                (new ApiResponse().builder()
                        .status(HttpStatus.OK.value())
                        .messages(messages)
                        .build(),
                        HttpStatus.OK);
    }

    //TAREA UPDATE
    @PutMapping("/tareas/{id}")
    public ResponseEntity<?> upateTarea(@PathVariable Integer id,
                                        @Valid @RequestBody CreateTareaRequest request){
        tareaService.updateTarea(id, request);
        Map<String, String> messages = new HashMap<>();
        messages.put("exito", "Tarea editada con éxito.");

        return new ResponseEntity<>
                (new ApiResponse().builder()
                        .status(HttpStatus.OK.value())
                        .messages(messages)
                        .build(),
                        HttpStatus.OK);
    };

    // DELETE
    @DeleteMapping("/tareas/{id}")
    public ResponseEntity<?> deleteTarea(@PathVariable Integer id){
        System.out.println(id);
        tareaService.deleteTarea(id);
        Map<String, String> messages = new HashMap<>();
        messages.put("exito", "Tarea eliminada con éxito.");

        return new ResponseEntity<>
                (new ApiResponse().builder()
                        .status(HttpStatus.OK.value())
                        .messages(messages)
                        .build(),
                        HttpStatus.OK);
    };
}
