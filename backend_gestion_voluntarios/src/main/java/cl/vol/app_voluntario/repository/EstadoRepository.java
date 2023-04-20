package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.model.Estado;

public interface EstadoRepository {
    Estado findByTareaId(Integer idTarea);

    Estado findById(Integer idEstado);
}
