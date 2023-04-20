package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.model.Coordinador;

public interface CoordinadorRepository {
    Coordinador save(Coordinador coordinador);
    Coordinador findByUserId(Integer idUsuario);
}
