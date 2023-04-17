package cl.vol.app_voluntario.controller;

import cl.vol.app_voluntario.model.Rol;
import cl.vol.app_voluntario.model.Usuario;
import cl.vol.app_voluntario.service.RolService;
import cl.vol.app_voluntario.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RolController {
    private final RolService rolService;

    @GetMapping("/roles")
    public List<Rol> getRoles(){
        return rolService.getRoles();
    }

}
