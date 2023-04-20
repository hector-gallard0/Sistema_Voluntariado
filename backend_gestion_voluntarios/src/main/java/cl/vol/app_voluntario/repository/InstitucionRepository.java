package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.model.Institucion;

public interface InstitucionRepository {
    Institucion findById(Integer idInstitucion);

    Institucion findByEmergenciaId(Integer idEmergencia);

    Institucion findByCoordinadorId(Integer idCoordinador);
}
