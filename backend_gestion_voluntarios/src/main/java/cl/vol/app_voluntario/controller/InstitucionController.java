package cl.vol.app_voluntario.controller;

import cl.vol.app_voluntario.service.InstitucionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class InstitucionController {

    private final InstitucionService institucionService;

    //READ ALL
    @GetMapping("/instituciones")
    public ResponseEntity<?> getInstituciones(){
        return new ResponseEntity<>(institucionService.getInstituciones(), HttpStatus.FOUND);
    }
}
