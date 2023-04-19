package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.model.Rol;
import cl.vol.app_voluntario.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class RolRepositoryImp implements RolRepository{
    @Autowired
    private Sql2o sql2o;

    @Override
    public Rol findById(int id) {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT * FROM rol WHERE id_rol = :id_rol";
            return con.createQuery(sql)
                    .addColumnMapping("id_rol", "id")
                    .addParameter("id_rol", id)
                    .executeAndFetchFirst(Rol.class);
        }catch(Error e){
            return null;
        }
    }
}
