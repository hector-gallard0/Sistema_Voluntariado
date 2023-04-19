package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.model.Coordinador;
import cl.vol.app_voluntario.model.Institucion;
import cl.vol.app_voluntario.model.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

@Repository
public class CoordinadorRepositoryImp implements CoordinadorRepository{

    @Autowired
    private Sql2o sql2o;

    @Override
    public boolean save(Coordinador coordinador) {
        try (Connection con = sql2o.open()) {
            Integer id = con.createQuery("SELECT nextval('coordinador_seq')")
                    .executeScalar(Integer.class);

            String sql = "INSERT INTO coordinador (id_coordinador, id_usuario, id_institucion)" +
                    "VALUES (:id_coordinador, :id_usuario, :id_institucion)";
            con.createQuery(sql)
                    .addColumnMapping("id_coordinador", "id")
                    .addParameter("id_coordinador", id)
                    .addParameter("id_usuario", coordinador.getUsuario().getId())
                    .addParameter("id_institucion", coordinador.getInstitucion().getId())
                    .executeUpdate();
            return true;
        }catch(Error e) {
            return false;
        }
    }

    @Override
    public Institucion findInstitucionByCoordinadorId(Integer id_coordinador) {
        try(Connection con = sql2o.open()){
            String rolSql = "SELECT i.* FROM coordinador c JOIN institucion i ON c.id_institucion = i.id_institucion AND c.id_coordinador = :id_coordinador";
            return con.createQuery(rolSql)
                    .addColumnMapping("id_institucion", "id")
                    .addColumnMapping("nombre", "nombre")
                    .addColumnMapping("descripcion", "descripcion")
                    .addParameter("id_coordinador", id_coordinador)
                    .executeAndFetchFirst(Institucion.class);
        }catch (Error e){
            throw new Error(e);
        }
    }
}
