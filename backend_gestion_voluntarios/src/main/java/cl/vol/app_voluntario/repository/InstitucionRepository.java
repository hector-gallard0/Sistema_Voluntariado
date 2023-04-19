package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.model.Institucion;

public interface InstitucionRepository {
    public Institucion findById(Integer id_institucion);
}
