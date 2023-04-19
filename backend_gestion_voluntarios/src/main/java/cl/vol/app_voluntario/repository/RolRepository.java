package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.model.Rol;

import java.util.List;

public interface RolRepository {
    public List<Rol> findRolesByUserId(int id_usuario);
}
