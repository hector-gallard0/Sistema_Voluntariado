package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {
    Usuario findByEmail(String email);
    List<Usuario> findAll();

    void save(Usuario usuario);
}
