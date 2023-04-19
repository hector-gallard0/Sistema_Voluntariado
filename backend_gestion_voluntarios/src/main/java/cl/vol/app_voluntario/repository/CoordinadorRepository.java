package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.model.Coordinador;
import cl.vol.app_voluntario.model.Institucion;

public interface CoordinadorRepository {
    public boolean save(Coordinador coordinador);

    Institucion findInstitucionByCoordinadorId(Integer id_coordinador);
}
