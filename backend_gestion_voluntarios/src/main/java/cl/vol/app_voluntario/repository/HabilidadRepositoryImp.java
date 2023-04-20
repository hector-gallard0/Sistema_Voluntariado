package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.model.Habilidad;
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
        try (Connection con = sql2o.beginTransaction()) {
            Integer id = con.createQuery("SELECT nextval('habilidad_seq')")
                    .executeScalar(Integer.class);
            String sql = "INSERT INTO habilidad (id_habilidad, descripcion) " +
                    "VALUES (:id_habilidad, :descripcion)";
            con.createQuery(sql)
                    .addColumnMapping("id_habilidad", "id")
                    .addParameter("id_habilidad", id)
                    .addParameter("descripcion", habilidad.getDescripcion())
                    .executeUpdate()
                    .getResult();
            con.commit();
            return findById(id);
        }
    }

    @Override
    public Habilidad findById(Integer idHabilidad) {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT h.id_habilidad, h.descripcion FROM habilidad h WHERE h.id_habilidad = :id_habilidad";
            return con.createQuery(sql)
                    .addColumnMapping("id_habilidad", "id")
                    .addParameter("id_habilidad", idHabilidad)
                    .executeAndFetchFirst(Habilidad.class);
        }
    }

    @Override
    public List<Habilidad> findAllByVoluntarioId(Integer idVoluntario) {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT h.id_habilidad, h.descripcion " +
                    "FROM habilidad h " +
                    "JOIN vol_habilidad vh ON vh.id_voluntario = :id_voluntario AND vh.id_habilidad = h.id_habilidad";
            return con.createQuery(sql)
                    .addColumnMapping("id_habilidad", "id")
                    .addParameter("id_voluntario", idVoluntario)
                    .executeAndFetch(Habilidad.class);
        }
    }
}
