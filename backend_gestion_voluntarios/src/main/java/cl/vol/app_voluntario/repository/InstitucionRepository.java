package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.model.Institucion;

import java.util.List;

public interface InstitucionRepository {
    Institucion findById(Integer idInstitucion);

    Institucion findByEmergenciaId(Integer idEmergencia);

    Institucion findByCoordinadorId(Integer idCoordinador);

    List<Institucion> findAll();
}
