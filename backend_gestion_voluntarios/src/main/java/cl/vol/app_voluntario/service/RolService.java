package cl.vol.app_voluntario.service;

import cl.vol.app_voluntario.model.Rol;
import cl.vol.app_voluntario.model.Usuario;
import cl.vol.app_voluntario.repository.RolRepository;
import cl.vol.app_voluntario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RolService {
    private final RolRepository rolRepository;

    public List<Rol> getRoles(){
        return rolRepository.findAll();
    }
}

