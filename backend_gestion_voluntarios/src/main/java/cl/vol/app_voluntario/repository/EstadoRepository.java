package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.model.Estado;

import java.util.List;

public interface EstadoRepository {
    List<Estado> findAll();
    Estado findByTareaId(Integer idTarea);

    Estado findById(Integer idEstado);
}
