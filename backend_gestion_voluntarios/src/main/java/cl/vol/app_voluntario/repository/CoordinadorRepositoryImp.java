package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.errors.QueryException;
import cl.vol.app_voluntario.model.Coordinador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

@Repository
public class CoordinadorRepositoryImp implements CoordinadorRepository{

    @Autowired
    private Sql2o sql2o;
    @Autowired
    private InstitucionRepository institucionRepository;

    @Override
    public Coordinador save(Coordinador coordinador) {
        try (Connection con = sql2o.beginTransaction()) {
            Integer id = con.createQuery("SELECT nextval('coordinador_seq')")
                    .executeScalar(Integer.class);
            String sql = "INSERT INTO coordinador (id_coordinador, id_usuario, id_institucion)" +
                    "VALUES (:id_coordinador, :id_usuario, :id_institucion)";
            con.createQuery(sql)
                    .addColumnMapping("id_coordinador", "id")
                    .addParameter("id_coordinador", id)
                    .addParameter("id_usuario", coordinador.getUsuario().getId())
                    .addParameter("id_institucion", coordinador.getInstitucion().getId())
                    .executeUpdate()
                    .getResult();
            con.commit();
            return findById(id);
        }
    }

    private Coordinador findById(Integer idCoordinador) {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT c.id_coordinador FROM coordinador c WHERE c.id_coordinador = :id_coordinador";
            Coordinador coordinador = con.createQuery(sql)
                    .addColumnMapping("id_coordinador", "id")
                    .addParameter("id_coordinador", idCoordinador)
                    .executeAndFetchFirst(Coordinador.class);
            if(coordinador == null) return null;
            coordinador.setInstitucion(institucionRepository.findByCoordinadorId(idCoordinador));
            return coordinador;
        }
    }

    @Override
    public Coordinador findByUserId(Integer idUsuario) {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT c.id_coordinador FROM coordinador c WHERE c.id_usuario = :id_usuario";
            Coordinador coordinador = con.createQuery(sql)
                    .addColumnMapping("id_coordinador", "id")
                    .addParameter("id_usuario", idUsuario)
                    .executeAndFetchFirst(Coordinador.class);
            if(coordinador == null) return null;
            coordinador.setInstitucion(institucionRepository.findByCoordinadorId(coordinador.getId()));
            return coordinador;
        }
    }
}
