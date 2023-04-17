package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.model.Voluntario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoluntarioRepository extends JpaRepository<Voluntario, Integer> {

}
