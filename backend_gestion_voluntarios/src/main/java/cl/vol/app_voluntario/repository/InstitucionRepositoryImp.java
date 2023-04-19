package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.model.Institucion;
import cl.vol.app_voluntario.model.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

@Repository
public class InstitucionRepositoryImp implements  InstitucionRepository{
    @Autowired
    private Sql2o sql2o;


    @Override
    public Institucion findById(Integer id_institucion) {
        try (Connection con = sql2o.open()) {
            Institucion institucion = null;
            String sql = "SELECT * FROM institucion WHERE id_institucion = :id_institucion";
            institucion = con.createQuery(sql)
                    .addColumnMapping("id_institucion", "id")
                    .addColumnMapping("nombre", "nombre")
                    .addColumnMapping("descripcion", "descripcion")
                    .addParameter("id_institucion", id_institucion)
                    .executeAndFetchFirst(Institucion.class);
            return institucion;
        }catch(Error e){
            throw new Error(e);
        }
    }
}
