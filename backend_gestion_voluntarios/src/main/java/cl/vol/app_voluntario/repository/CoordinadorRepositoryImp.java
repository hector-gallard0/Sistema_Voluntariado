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
        try (Connection con = sql2o.open()) {
            Integer id = con.createQuery("SELECT nextval('coordinador_seq')")
                    .executeScalar(Integer.class);
            String sql = "INSERT INTO coordinador (id_coordinador, id_usuario, id_institucion)" +
                    "VALUES (:idCoordinador, :idUsuario, :idInstitucion)";
            con.createQuery(sql)
                    .addColumnMapping("id_coordinador", "id")
                    .addParameter("idCoordinador", id)
                    .addParameter("idUsuario", coordinador.getUsuario().getId())
                    .addParameter("idInstitucion", coordinador.getInstitucion().getId())
                    .executeUpdate();
            return findById(id);
        }catch(Exception e){
            throw new QueryException("No se pudo crear el coordinador.\n" + e.getMessage());
        }
    }

    private Coordinador findById(Integer idCoordinador) {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT id_coordinador FROM coordinador WHERE id_coordinador = :idCoordinador";
            Coordinador coordinador = con.createQuery(sql)
                    .addColumnMapping("id_coordinador", "id")
                    .addParameter("idCoordinador", idCoordinador)
                    .executeAndFetchFirst(Coordinador.class);
            coordinador.setInstitucion(institucionRepository.findByCoordinadorId(idCoordinador));
            return coordinador;
        }catch (Exception e){
            throw new QueryException("Coordinador no encontrado\n." + e.getMessage());
        }
    }

    @Override
    public Coordinador findByUserId(Integer idUsuario) {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT c.id_coordinador FROM coordinador c WHERE c.id_usuario = :idUsuario";
            Coordinador coordinador = con.createQuery(sql)
                    .addColumnMapping("id_coordinador", "id")
                    .addParameter("idUsuario", idUsuario)
                    .executeAndFetchFirst(Coordinador.class);
            coordinador.setInstitucion(institucionRepository.findByCoordinadorId(coordinador.getId()));
            return coordinador;
        }catch (Exception e){
            throw new QueryException("Coordinador no encontrado.\n" + e.getMessage());
        }
    }
}
