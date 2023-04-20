package cl.vol.app_voluntario.controller;

import cl.vol.app_voluntario.model.Habilidad;
import cl.vol.app_voluntario.service.VoluntarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class VoluntarioController {
    private final VoluntarioService voluntarioService;

    @PutMapping("/voluntarios/{idVoluntario}/habilidades/{idHabilidad}")
    public Habilidad addHabilidadVoluntario(
            @PathVariable Integer idVoluntario,
            @PathVariable Integer idHabilidad
    ){
        return voluntarioService.addHabilidadVoluntario(idVoluntario, idHabilidad);
    }
}
