package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.model.Institucion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstitucionRepository extends JpaRepository<Institucion, Integer> {
    @Override
    Optional<Institucion> findById(Integer integer);
}
