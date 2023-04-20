package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.model.Institucion;
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
            String sql = "SELECT * FROM institucion WHERE id_institucion = :id_institucion";
            return con.createQuery(sql)
                    .addColumnMapping("id_institucion", "id")
                    .addColumnMapping("nombre", "nombre")
                    .addColumnMapping("descripcion", "descripcion")
                    .addParameter("id_institucion", idInstitucion)
                    .executeAndFetchFirst(Institucion.class);
        }
    }

    @Override
    public Institucion findByEmergenciaId(Integer idEmergencia) {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT i.* FROM emergencia e JOIN institucion i ON e.id_institucion = i.id_institucion AND e.id_emergencia = :id_emergencia";
            return con.createQuery(sql)
                    .addColumnMapping("id_institucion", "id")
                    .addColumnMapping("nombre", "nombre")
                    .addColumnMapping("descripcion", "descripcion")
                    .addParameter("id_emergencia", idEmergencia)
                    .executeAndFetchFirst(Institucion.class);
        }
    }

    @Override
    public Institucion findByCoordinadorId(Integer idCoordinador) {
        try(Connection con = sql2o.open()){
            String rolSql = "SELECT i.* FROM coordinador c JOIN institucion i ON c.id_institucion = i.id_institucion AND c.id_coordinador = :id_coordinador";
            return con.createQuery(rolSql)
                    .addColumnMapping("id_institucion", "id")
                    .addColumnMapping("nombre", "nombre")
                    .addColumnMapping("descripcion", "descripcion")
                    .addParameter("id_coordinador", idCoordinador)
                    .executeAndFetchFirst(Institucion.class);
        }
    }
}
