package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.model.Habilidad;
import cl.vol.app_voluntario.model.Voluntario;

public interface VoluntarioRepository {
    Voluntario save(Voluntario voluntario);
    Voluntario findVoluntarioByUserId(Integer idUsuario);

    Voluntario findById(Integer idUsuario);
    Habilidad saveVolHabilidad(Integer idVoluntario, Integer idHabilidad);
}
