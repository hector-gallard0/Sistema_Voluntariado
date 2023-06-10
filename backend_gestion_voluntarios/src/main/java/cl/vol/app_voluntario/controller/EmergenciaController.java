package cl.vol.app_voluntario.controller;

import cl.vol.app_voluntario.model.Emergencia;
import cl.vol.app_voluntario.model.Usuario;
import cl.vol.app_voluntario.repository.EmergenciaRepository;
import cl.vol.app_voluntario.request.CreateEmergenciaRequest;
import cl.vol.app_voluntario.request.CreateTareaRequest;
import cl.vol.app_voluntario.request.RegisterRequest;
import cl.vol.app_voluntario.request.UpdateEmergenciaRequest;
import cl.vol.app_voluntario.response.ApiResponse;
import cl.vol.app_voluntario.response.AuthenticationResponse;
import cl.vol.app_voluntario.service.EmergenciaService;
import cl.vol.app_voluntario.util.ValidationUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EmergenciaController {
    private final EmergenciaService emergenciaService;
    //CREATE
    @PostMapping("/emergencias")
    public ResponseEntity<?> createEmergencia(
            @Valid @RequestBody CreateEmergenciaRequest request,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()) return new ResponseEntity<>(ValidationUtil.getValidationErrors(bindingResult), HttpStatus.BAD_REQUEST);
        return emergenciaService.createEmergencia(request);
    }

    //READ ALL
    @GetMapping("/emergencias")
    public ResponseEntity<?> getEmergencias(){
        return new ResponseEntity<>(emergenciaService.getEmergencias(), HttpStatus.OK);
    };

    //TAREA UPDATE
    @PutMapping("/emergencias/{id}")
    public ResponseEntity<?> updateEmergencia(@PathVariable Integer id,
                                        @Valid @RequestBody UpdateEmergenciaRequest request){
        emergenciaService.updateEmergencia(id, request);
        Map<String, String> messages = new HashMap<>();
        messages.put("exito", "Emergencia editada con éxito.");

        return new ResponseEntity<>
                (new ApiResponse().builder()
                        .status(HttpStatus.OK.value())
                        .messages(messages)
                        .build(),
                        HttpStatus.OK);
    };
}
