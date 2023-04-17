package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Integer> {
    @Override
    Optional<Rol> findById(Integer integer);

    @Override
    List<Rol> findAll();
}
