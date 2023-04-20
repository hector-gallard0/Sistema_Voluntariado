package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.errors.QueryException;
import cl.vol.app_voluntario.model.Institucion;
import cl.vol.app_voluntario.model.Rol;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

@Repository
public class InstitucionRepositoryImp implements  InstitucionRepository{
    @Autowired
    private Sql2o sql2o;
    @Override
    public Institucion findById(Integer idInstitucion) {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT * FROM institucion WHERE id_institucion = :idInstitucion";
            return con.createQuery(sql)
                    .addColumnMapping("id_institucion", "id")
                    .addColumnMapping("nombre", "nombre")
                    .addColumnMapping("descripcion", "descripcion")
                    .addParameter("idInstitucion", idInstitucion)
                    .executeAndFetchFirst(Institucion.class);
        }catch (Exception e){
            throw new QueryException("Institución no encontrada.\n" + e.getMessage());
        }
    }

    @Override
    public Institucion findByEmergenciaId(Integer idEmergencia) {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT i.* FROM emergencia e JOIN institucion i ON e.id_institucion = i.id_institucion AND e.id_emergencia = :idEmergencia";
            return con.createQuery(sql)
                    .addColumnMapping("id_institucion", "id")
                    .addColumnMapping("nombre", "nombre")
                    .addColumnMapping("descripcion", "descripcion")
                    .addParameter("idEmergencia", idEmergencia)
                    .executeAndFetchFirst(Institucion.class);
        }catch (Exception e){
            throw new QueryException("Institución no encontrada.\n" + e.getMessage());
        }
    }

    @Override
    public Institucion findByCoordinadorId(Integer idCoordinador) {
        try(Connection con = sql2o.open()){
            String rolSql = "SELECT i.* FROM coordinador c JOIN institucion i ON c.id_institucion = i.id_institucion AND c.id_coordinador = :idCoordinador";
            return con.createQuery(rolSql)
                    .addColumnMapping("id_institucion", "id")
                    .addColumnMapping("nombre", "nombre")
                    .addColumnMapping("descripcion", "descripcion")
                    .addParameter("idCoordinador", idCoordinador)
                    .executeAndFetchFirst(Institucion.class);
        }catch (Exception e){
            throw new QueryException("Institución no encontrada.\n" + e.getMessage());
        }
    }
}
