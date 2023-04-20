package cl.vol.app_voluntario.controller;

import cl.vol.app_voluntario.model.Habilidad;
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

@RestController
@RequestMapping("/api/v1/voluntarios")
@RequiredArgsConstructor
public class VoluntarioController {
    private final VoluntarioService voluntarioService;

    @PutMapping("/{idVoluntario}/habilidades/{idHabilidad}")
    public ResponseEntity<?> addHabilidadVoluntario(
            @Valid @NotNull @PathVariable Integer idVoluntario,
            @Valid @NotNull @PathVariable Integer idHabilidad,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()) return new ResponseEntity<>(ValidationUtil.getValidationErrors(bindingResult), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(voluntarioService.addHabilidadVoluntario(idVoluntario, idHabilidad), HttpStatus.OK);
    }
}
