package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.errors.QueryException;
import cl.vol.app_voluntario.model.Emergencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class EmergenciaRepositoryImp implements EmergenciaRepository{
    @Autowired
    private Sql2o sql2o;
    @Autowired
    private InstitucionRepository institucionRepository;
    @Override
    public Emergencia save(Emergencia emergencia) {
        try (Connection con = sql2o.open()) {
            Integer id = con.createQuery("SELECT nextval('emergencia_seq')")
                    .executeScalar(Integer.class);

            String sql = "INSERT INTO emergencia (id_emergencia, nombre, descripcion, fecha_inicio, fecha_fin, id_institucion) " +
                    "VALUES (:idEmergencia, :nombre, :descripcion, :fechaInicio, :fechaFin, :idInstitucion)";
            con.createQuery(sql)
                    .addColumnMapping("id_emergencia", "id")
                    .addColumnMapping("nombre", "nombre")
                    .addColumnMapping("descripcion", "descripcion")
                    .addColumnMapping("fecha_inicio", "fechaInicio")
                    .addColumnMapping("fecha_fin", "fechaFin")
                    .addParameter("idEmergencia", id)
                    .addParameter("nombre", emergencia.getNombre())
                    .addParameter("descripcion", emergencia.getDescripcion())
                    .addParameter("fechaInicio", emergencia.getFechaInicio())
                    .addParameter("fechaFin", emergencia.getFechaFin())
                    .addParameter("idInstitucion", emergencia.getInstitucion().getId())
                    .executeUpdate();
            return findById(id);
        }catch(Exception e){
            throw new QueryException("No se pudo crear la emergencia.\n" + e.getMessage());
        }
    }

    @Override
    public Emergencia findById(Integer idEmergencia) {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT id_emergencia, nombre, descripcion, fecha_inicio, fecha_fin FROM emergencia WHERE id_emergencia = :idEmergencia";
            Emergencia emergencia = con.createQuery(sql)
                    .addColumnMapping("id_emergencia", "id")
                    .addColumnMapping("fecha_inicio", "fechaInicio")
                    .addColumnMapping("fecha_fin", "fechaFin")
                    .addParameter("idEmergencia", idEmergencia)
                    .executeAndFetchFirst(Emergencia.class);
            emergencia.setInstitucion(institucionRepository.findByEmergenciaId(idEmergencia));
            return emergencia;
        }catch (Exception e){
            throw new QueryException("Emergencia no encontrada.\n" + e.getMessage());
        }
    }

    @Override
    public List<Emergencia> findAll() {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT id_emergencia, nombre, descripcion, fecha_inicio, fecha_fin FROM emergencia";
            List<Emergencia> emergencias = con.createQuery(sql)
                    .addColumnMapping("id_emergencia", "id" )
                    .addColumnMapping("fecha_inicio", "fechaInicio")
                    .addColumnMapping("fecha_fin", "fechaFin")
                    .executeAndFetch(Emergencia.class);
            for (Emergencia emergencia : emergencias) {
                emergencia.setInstitucion(institucionRepository.findByEmergenciaId(emergencia.getId()));
            }
            return emergencias;
        }catch (Exception e){
            throw new QueryException("Emergencias no encontradas.\n" + e.getMessage());
        }
    }

    @Override
    public Emergencia findByTareaId(Integer idTarea) {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT e.id_emergencia, e.nombre, e.descripcion, e.fecha_inicio, e.fecha_fin FROM emergencia e JOIN tarea t ON t.id_emergencia = e.id_emergencia AND t.id_tarea = :idTarea";
            Emergencia emergencia = con.createQuery(sql)
                    .addColumnMapping("id_emergencia", "id" )
                    .addColumnMapping("fecha_inicio", "fechaInicio")
                    .addColumnMapping("fecha_fin", "fechaFin")
                    .addParameter("idTarea", idTarea)
                    .executeAndFetchFirst(Emergencia.class);
            //ACA FALTAN LAS HABILIDADES Y TAREAS
            emergencia.setInstitucion(institucionRepository.findByEmergenciaId(emergencia.getId()));
            return emergencia;
        }catch(Exception e){
            throw new QueryException("Emergencia no encontrada.\n" + e.getMessage());
        }
    }
}
