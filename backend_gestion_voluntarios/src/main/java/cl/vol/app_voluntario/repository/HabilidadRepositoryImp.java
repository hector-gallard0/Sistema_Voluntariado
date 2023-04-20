package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.errors.QueryException;
import cl.vol.app_voluntario.model.Habilidad;
import cl.vol.app_voluntario.model.Tarea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class HabilidadRepositoryImp implements  HabilidadRepository{
    @Autowired
    private Sql2o sql2o;

    @Override
    public Habilidad save(Habilidad habilidad) {
        try (Connection con = sql2o.open()) {
            Integer id = con.createQuery("SELECT nextval('habilidad_seq')")
                    .executeScalar(Integer.class);
            String sql = "INSERT INTO habilidad (id_habilidad, descripcion) " +
                    "VALUES (:id_habilidad, :descripcion)";
            Integer rows = con.createQuery(sql)
                    .addColumnMapping("id_habilidad", "id")
                    .addParameter("id_habilidad", id)
                    .addParameter("descripcion", habilidad.getDescripcion())
                    .executeUpdate()
                    .getResult();
            return findById(id);
        }catch (Exception e){
            throw new QueryException("Error al crear la habilidad.\n" + e.getMessage());
        }
    }

    @Override
    public Habilidad findById(Integer idHabilidad) {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT id_habilidad, descripcion FROM habilidad WHERE id_habilidad = :idHabilidad";
            return con.createQuery(sql)
                    .addColumnMapping("id_habilidad", "id")
                    .addParameter("idHabilidad", idHabilidad)
                    .executeAndFetchFirst(Habilidad.class);
        }catch (Exception e){
            throw new QueryException("Habilidad no encontrada.\n" + e.getMessage());
        }
    }

    @Override
    public List<Habilidad> findAllByVoluntarioId(Integer idVoluntario) {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT id_habilidad, descripcion " +
                    "FROM habilidad h " +
                    "JOIN vol_habilidad vh ON vh.id_voluntario = :idVoluntario AND vh.id_habilidad = h.id_habilidad";
            List<Habilidad> habilidades = con.createQuery(sql)
                    .addColumnMapping("id_habilidad", "id")
                    .addParameter("idVoluntario", idVoluntario)
                    .executeAndFetch(Habilidad.class);
            return habilidades;
        }catch(Exception e){
            throw new QueryException("Habilidades no encontradas.\n" + e.getMessage());
        }
    }
}
