package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.model.Rol;
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
    public List<Rol> findRolesByUserId(int id_usuario) {
        try(Connection con = sql2o.open()){
            String rolSql = "SELECT r.* FROM rol r INNER JOIN usuario_rol ur ON r.id_rol = ur.id_rol WHERE ur.id_usuario = :id_usuario";
            return con.createQuery(rolSql)
                    .addColumnMapping("id_rol", "id")
                    .addParameter("id_usuario", id_usuario)
                    .executeAndFetch(Rol.class);
        }
    }
}
