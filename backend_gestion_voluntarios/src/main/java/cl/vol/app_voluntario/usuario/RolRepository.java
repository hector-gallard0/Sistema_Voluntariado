package cl.vol.app_voluntario.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Integer> {
    @Override
    Optional<Rol> findById(Integer integer);
}
