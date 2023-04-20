package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.errors.QueryException;
import cl.vol.app_voluntario.model.Emergencia;
import cl.vol.app_voluntario.model.Estado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

@Repository
public class EstadoRepositoryImp implements EstadoRepository{
    @Autowired
    private Sql2o sql2o;

    @Override
    public Estado findByTareaId(Integer idTarea) {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT e.* FROM estado e JOIN tarea t ON t.id_estado = e.id_estado AND t.id_tarea = :idTarea";
            return con.createQuery(sql)
                    .addColumnMapping("id_estado", "id")
                    .addParameter("idTarea", idTarea)
                    .executeAndFetchFirst(Estado.class);
        }catch(Exception e){
            throw new QueryException("Estado no encontrado.\n" + e.getMessage());
        }
    }

    @Override
    public Estado findById(Integer idEstado) {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT * FROM estado WHERE id_estado = :idEstado";
            return con.createQuery(sql)
                    .addColumnMapping("id_estado", "id")
                    .addParameter("idEstado", idEstado)
                    .executeAndFetchFirst(Estado.class);
        }catch(Exception e){
            throw new QueryException("Estado no encontrado.\n" + e.getMessage());
        }
    }
}
