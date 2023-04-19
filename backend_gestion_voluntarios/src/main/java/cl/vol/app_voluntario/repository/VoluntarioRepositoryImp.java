package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.model.Coordinador;
import cl.vol.app_voluntario.model.Voluntario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

@Repository
public class VoluntarioRepositoryImp implements VoluntarioRepository{
    @Autowired
    private Sql2o sql2o;

    @Override
    public boolean save(Voluntario voluntario) {
        try (Connection con = sql2o.open()) {
            Integer id = con.createQuery("SELECT nextval('voluntario_seq')")
                    .executeScalar(Integer.class);

            String sql = "INSERT INTO voluntario (id_voluntario, id_usuario)" +
                    "VALUES (:id_voluntario, :id_usuario)";
            con.createQuery(sql)
                    .addColumnMapping("id_voluntario", "id")
                    .addParameter("id_voluntario", id)
                    .addParameter("id_usuario", voluntario.getUsuario().getId())
                    .executeUpdate();
            return true;
        }catch(Error e) {
            return false;
        }
    }
}
