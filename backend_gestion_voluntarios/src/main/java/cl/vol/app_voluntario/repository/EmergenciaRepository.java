package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.model.Emergencia;
import cl.vol.app_voluntario.model.Tarea;

import java.util.List;

public interface EmergenciaRepository {
    Emergencia save(Emergencia emergencia);
    Emergencia findById(Integer idEmergencia);
    List<Emergencia> findAll();

    Emergencia findByTareaId(Integer idTarea);
    Integer findEmeHabilidadIdByHabilidadId(Integer idHabilidad);

    List<Emergencia> findAllByHabilidadId(Integer idHabilidad);

    void set(Emergencia emergencia);
}
