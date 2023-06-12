package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.model.Habilidad;
import cl.vol.app_voluntario.model.Voluntario;
import cl.vol.app_voluntario.request.CreateVolTareaRequest;
import cl.vol.app_voluntario.util.TransactionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.sql.SQLException;

@Repository
public class VoluntarioRepositoryImp implements VoluntarioRepository{
    @Autowired
    private Sql2o sql2o;
    @Autowired
    private HabilidadRepository habilidadRepository;
    @Autowired
    private TareaRepository tareaRepository;

    @Override
    public Voluntario save(Voluntario voluntario) {
        try (Connection con = sql2o.beginTransaction()) {
            Integer id = con.createQuery("SELECT nextval('voluntario_seq')")
                    .executeScalar(Integer.class);
            String sql = "INSERT INTO voluntario (id_voluntario, id_usuario) " +
                    "VALUES (:id_voluntario, :id_usuario)";
            TransactionUtil.createTempTableWithUsername(con, sql);
            con.createQuery(sql)
                    .addColumnMapping("id_voluntario", "id")
                    .addParameter("id_voluntario", id)
                    .addParameter("id_usuario", voluntario.getUsuario().getId())
                    .executeUpdate()
                    .getResult();
            con.commit();
            return findById(id);
        }
    }

    @Override
    public Voluntario findById(Integer idVoluntario) {
        try(Connection con = sql2o.open()){
            String rolSql = "SELECT id_voluntario FROM voluntario WHERE id_voluntario = :id_voluntario";
            Voluntario voluntario = con.createQuery(rolSql)
                    .addColumnMapping("id_voluntario", "id")
                    .addParameter("id_voluntario", idVoluntario)
                    .executeAndFetchFirst(Voluntario.class);
            if(voluntario == null) return null;
            voluntario.setTareas(tareaRepository.findAllByVoluntarioId(idVoluntario));
            voluntario.setHabilidades(habilidadRepository.findAllByVoluntarioId(idVoluntario));
            return voluntario;
        }
    }

    @Override
    public Voluntario findVoluntarioByUserId(Integer idUsuario){
        try(Connection con = sql2o.open()){
            String rolSql = "SELECT id_voluntario FROM voluntario WHERE id_usuario = :id_usuario";
            return con.createQuery(rolSql)
                    .addColumnMapping("id_voluntario", "id")
                    .addParameter("id_usuario", idUsuario)
                    .executeAndFetchFirst(Voluntario.class);
        }
    }

    @Override
    public Habilidad saveVolHabilidad(Integer idVoluntario, Integer idHabilidad) {
        try (Connection con = sql2o.open()) {
            con.getJdbcConnection().setAutoCommit(false);
            Integer id = con.createQuery("SELECT nextval('vol_habilidad_seq')")
                    .executeScalar(Integer.class);
            String sql = "INSERT INTO vol_habilidad (id_voluntario_habilidad, id_voluntario, id_habilidad) " +
                    "VALUES (:id_voluntario_habilidad, :id_voluntario, :id_voluntario)";
            TransactionUtil.createTempTableWithUsername(con, sql);
            con.createQuery(sql)
                    .addParameter("id_voluntario_habilidad", id)
                    .addParameter("id_voluntario", idVoluntario)
                    .addParameter("id_habilidad", idHabilidad)
                    .executeUpdate()
                    .getResult();
            con.commit();
            return habilidadRepository.findById(idHabilidad);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void saveVolTarea(Integer idVoluntario, Integer idTarea, CreateVolTareaRequest request) {
        try (Connection con = sql2o.open()) {
            con.getJdbcConnection().setAutoCommit(false);
            Integer id = con.createQuery("SELECT nextval('ranking_seq')")
                    .executeScalar(Integer.class);
            String sql = "INSERT INTO ranking (id_ranking, id_voluntario, id_tarea, puntaje, flag_invitado, flag_participa) " +
                    "VALUES (:id_ranking, :id_voluntario, :id_tarea, :puntaje, :flag_invitado, :flag_participa)";
            TransactionUtil.createTempTableWithUsername(con, sql);
            con.createQuery(sql)
                    .addParameter("id_ranking", id)
                    .addParameter("id_voluntario", idVoluntario)
                    .addParameter("id_tarea", idTarea)
                    .addParameter("puntaje", request.getPuntaje())
                    .addParameter("flag_invitado", request.getFlag_invitado())
                    .addParameter("flag_participa", request.getFlag_participa())
                    .executeUpdate()
                    .getResult();
            con.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteVolHabilidadByVoluntarioId(Integer idVoluntario){
        try (Connection con = sql2o.open()) {
            String sql = "DELETE FROM vol_habilidad WHERE id_voluntario = :id_voluntario; ";

            TransactionUtil.createTempTableWithUsername(con, sql);
            Integer res = con.createQuery(sql)
                    .addParameter("id_voluntario", idVoluntario)
                    .executeUpdate()
                    .getResult();
        }
    }

    public void delete(Integer idVoluntario){
        try (Connection con = sql2o.open()) {
            String sql = "DELETE FROM voluntario WHERE id_voluntario = :id_voluntario; ";

            TransactionUtil.createTempTableWithUsername(con, sql);
            Integer res = con.createQuery(sql)
                    .addParameter("id_voluntario", idVoluntario)
                    .executeUpdate()
                    .getResult();
        }
    }
}
