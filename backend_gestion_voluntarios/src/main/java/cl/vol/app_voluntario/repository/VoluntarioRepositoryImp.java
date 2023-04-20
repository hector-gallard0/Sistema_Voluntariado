package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.errors.QueryException;
import cl.vol.app_voluntario.model.Habilidad;
import cl.vol.app_voluntario.model.Voluntario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

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
        try (Connection con = sql2o.open()) {
            Integer id = con.createQuery("SELECT nextval('voluntario_seq')")
                    .executeScalar(Integer.class);

            String sql = "INSERT INTO voluntario (id_voluntario, id_usuario)" +
                    "VALUES (:idVoluntario, :idUsuario)";
            con.createQuery(sql)
                    .addColumnMapping("id_voluntario", "id")
                    .addParameter("idVoluntario", id)
                    .addParameter("idUsuario", voluntario.getUsuario().getId())
                    .executeUpdate();
            return findById(id);
        }catch(Exception e) {
            throw new QueryException("No se pudo crear el voluntario.\n" + e.getMessage());
        }
    }

    @Override
    public Voluntario findById(Integer idVoluntario) {
        try(Connection con = sql2o.open()){
            String rolSql = "SELECT id_voluntario FROM voluntario WHERE id_voluntario = :idVoluntario";
            Voluntario voluntario = con.createQuery(rolSql)
                    .addColumnMapping("id_voluntario", "id")
                    .addParameter("id_usuario", idVoluntario)
                    .executeAndFetchFirst(Voluntario.class);
            voluntario.setTareas(tareaRepository.findAllByVoluntarioId(idVoluntario));
            voluntario.setHabilidades(habilidadRepository.findAllByVoluntarioId(idVoluntario));
            return voluntario;
        }catch (Exception e){
            throw new QueryException("Voluntario no encontrado.\n" + e.getMessage());
        }
    }

    @Override
    public Voluntario findVoluntarioByUserId(Integer idUsuario){
        try(Connection con = sql2o.open()){
            Voluntario voluntario = null;
            String rolSql = "SELECT id_voluntario FROM voluntario WHERE id_usuario = :idUsuario";
            voluntario = con.createQuery(rolSql)
                    .addColumnMapping("id_voluntario", "id")
                    .addParameter("id_usuario", idUsuario)
                    .executeAndFetchFirst(Voluntario.class);
            return voluntario;
        }catch (Exception e){
            throw new QueryException("Voluntario no encontrado.\n" + e.getMessage());
        }
    }

    @Override
    public Habilidad saveVolHabilidad(Integer idVoluntario, Integer idHabilidad) {
        try (Connection con = sql2o.open()) {
            Integer id = con.createQuery("SELECT nextval('vol_habilidad_seq')")
                    .executeScalar(Integer.class);
            String sql = "INSERT INTO vol_habilidad (id_voluntario_habilidad, id_voluntario, id_habilidad)" +
                    "VALUES (:idVoluntarioHabilidad, :idVoluntario, :idHabilidad)";
            con.createQuery(sql)
                    .addParameter("idVoluntarioHabilidad", id)
                    .addParameter("idVoluntario", idVoluntario)
                    .addParameter("idHabilidad", idHabilidad)
                    .executeUpdate();
            return habilidadRepository.findById(idHabilidad);
        }catch(Exception e) {
            throw new QueryException("No se pudo crear la habilidad para el usuario.\n" + e.getMessage());
        }
    }
}
