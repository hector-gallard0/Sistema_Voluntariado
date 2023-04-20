package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.model.Tarea;

import java.util.List;

public interface TareaRepository {
    Tarea save(Tarea tarea);

    Tarea findById(Integer idTarea);

    List<Tarea> findAllByVoluntarioId(Integer idVoluntario);
}
