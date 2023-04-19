package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {
    Usuario findByEmail(String email);
    List<Usuario> findAll();

    boolean save(Usuario usuario);

    Usuario findById(int id);
}
