package cl.vol.app_voluntario.controller;

import cl.vol.app_voluntario.model.Emergencia;
import cl.vol.app_voluntario.model.Usuario;
import cl.vol.app_voluntario.request.CreateEmergenciaRequest;
import cl.vol.app_voluntario.request.RegisterRequest;
import cl.vol.app_voluntario.response.AuthenticationResponse;
import cl.vol.app_voluntario.service.EmergenciaService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EmergenciaController {
    private final EmergenciaService emergenciaService;

    //CREATE
    @PostMapping("/emergencias")
    public ResponseEntity<Emergencia> createEmergencia(
            @RequestBody CreateEmergenciaRequest request
    ){
        return new ResponseEntity<>(emergenciaService.createEmergencia(request), HttpStatus.CREATED);
    }

    //READ ALL
    @GetMapping("/emergencias")
    public List<Emergencia> getEmergencias(){
        return emergenciaService.getEmergencias();
    };
}
