package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.model.Estado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class EstadoRepositoryImp implements EstadoRepository{
    @Autowired
    private Sql2o sql2o;

    @Override
    public Estado findByTareaId(Integer idTarea) {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT e.* FROM estado e JOIN tarea t ON t.id_estado = e.id_estado AND t.id_tarea = :id_tarea";
            return con.createQuery(sql)
                    .addColumnMapping("id_estado", "id")
                    .addParameter("id_tarea", idTarea)
                    .executeAndFetchFirst(Estado.class);
        }
    }

    @Override
    public Estado findById(Integer idEstado) {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT e.*  FROM estado e WHERE e.id_estado = :id_estado";
            return con.createQuery(sql)
                    .addColumnMapping("id_estado", "id")
                    .addParameter("id_estado", idEstado)
                    .executeAndFetchFirst(Estado.class);
        }
    }

    @Override
    public List<Estado> findAll() {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT e.*  FROM estado e";
            return con.createQuery(sql)
                    .addColumnMapping("id_estado", "id")
                    .executeAndFetch(Estado.class);
        }
    }
}
