package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.errors.QueryException;
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
    public Rol findById(int idRol) {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT * FROM rol WHERE id_rol = :idRol";
            return con.createQuery(sql)
                    .addColumnMapping("id_rol", "id")
                    .addParameter("idRol", idRol)
                    .executeAndFetchFirst(Rol.class);
        }catch(Exception e){
            throw new QueryException("Rol no encontrado.\n" + e.getMessage());
        }
    }

    @Override
    public List<Rol> findByUserId(int idUsuario) {
        try(Connection con = sql2o.open()){
            String rolSql = "SELECT r.* FROM rol r INNER JOIN usuario_rol ur ON r.id_rol = ur.id_rol WHERE ur.id_usuario = :idUsuario";
            return con.createQuery(rolSql)
                    .addColumnMapping("id_rol", "id")
                    .addParameter("idUsuario", idUsuario)
                    .executeAndFetch(Rol.class);
        }catch (Exception e){
            throw new QueryException("Roles no encontrados.\n" + e.getMessage());
        }
    }
}
