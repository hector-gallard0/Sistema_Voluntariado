package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.model.Rol;
import cl.vol.app_voluntario.model.Usuario;

import java.util.List;

public interface UsuarioRepository {
    Usuario findByEmail(String email);
    List<Usuario> findAll();
    List<Usuario> findAllByRoleId(Integer idRol);
    Usuario save(Usuario usuario);
    Usuario findById(Integer idUsuario);
    Usuario saveUserRoles(Integer idUsuario, List<Rol> roles);

    Usuario findByCoordinadorId(Integer idCoordinador);
}
