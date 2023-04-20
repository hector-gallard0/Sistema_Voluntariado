package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.errors.QueryException;
import cl.vol.app_voluntario.model.Emergencia;
import cl.vol.app_voluntario.model.Estado;
import cl.vol.app_voluntario.model.Tarea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class TareaRepositoryImp implements TareaRepository{
    @Autowired
    private Sql2o sql2o;
    @Autowired
    private EmergenciaRepository emergenciaRepository;
    @Autowired
    private EstadoRepository estadoRepository;
    @Override
    public Tarea save(Tarea tarea) {
        try (Connection con = sql2o.open()) {
            Integer id = con.createQuery("SELECT nextval('tarea_seq')")
                    .executeScalar(Integer.class);

            String sql = "INSERT INTO tarea (id_tarea, nombre, descripcion, cant_vol_requeridos, cant_vol_inscritos, fecha_inicio, fecha_fin, id_emergencia, id_estado) " +
                    "VALUES (:idTarea, :nombre, :descripcion, :voluntariosRequeridos, :voluntariosInscritos, :fechaInicio, :fechaFin, :idEmergencia, :idEstado)";
            con.createQuery(sql)
                    .addColumnMapping("id_tarea", "id")
                    .addColumnMapping("cant_vol_requeridos", "voluntariosRequeridos")
                    .addColumnMapping("cant_vol_inscritos", "voluntariosInscritos")
                    .addColumnMapping("fecha_inicio", "fechaInicio")
                    .addColumnMapping("fecha_fin", "fechaFin")
                    .addParameter("idTarea", id)
                    .addParameter("nombre", tarea.getNombre())
                    .addParameter("descripcion", tarea.getDescripcion())
                    .addParameter("voluntariosRequeridos", tarea.getVoluntariosRequeridos())
                    .addParameter("voluntariosInscritos", tarea.getVoluntariosInscritos())
                    .addParameter("fechaInicio", tarea.getFechaInicio())
                    .addParameter("fechaFin", tarea.getFechaFin())
                    .addParameter("idEmergencia", tarea.getEmergencia().getId())
                    .addParameter("idEstado", tarea.getEstado().getId())
                    .executeUpdate();
            return findById(id);
        }catch(Exception e) {
            throw new QueryException("No se pudo crear la tarea.\n" + e.getMessage());
        }
    }

    @Override
    public Tarea findById(Integer idTarea) {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT id_tarea, nombre, descripcion, cant_vol_requeridos, cant_vol_inscritos, fecha_inicio, fecha_fin FROM tarea WHERE id_tarea = :idTarea";
            Tarea tarea = con.createQuery(sql)
                    .addColumnMapping("id_tarea", "id")
                    .addColumnMapping("cant_vol_requeridos", "voluntariosRequeridos")
                    .addColumnMapping("cant_vol_inscritos", "voluntariosInscritos")
                    .addColumnMapping("fecha_inicio", "fechaInicio")
                    .addColumnMapping("fecha_fin", "fechaFin")
                    .addParameter("id_tarea", idTarea)
                    .executeAndFetchFirst(Tarea.class);
            tarea.setEmergencia(emergenciaRepository.findByTareaId(tarea.getId()));
            tarea.setEstado(estadoRepository.findByTareaId(tarea.getId()));
            return tarea;
        }catch(Exception e){
            throw new QueryException("Tarea no encontrada.\n" + e.getMessage());
        }
    }

    @Override
    public List<Tarea> findAllByVoluntarioId(Integer idVoluntario) {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT id_tarea, nombre, descripcion, cant_vol_requeridos, cant_vol_inscritos, fecha_inicio, fecha_fin " +
                    "FROM tarea t " +
                    "JOIN ranking r ON r.id_voluntario = :idVoluntario AND r.id_tarea = t.id_tarea";
            List<Tarea> tareas = con.createQuery(sql)
                    .addColumnMapping("id_tarea", "id")
                    .addColumnMapping("cant_vol_requeridos", "voluntariosRequeridos")
                    .addColumnMapping("cant_vol_inscritos", "voluntariosInscritos")
                    .addColumnMapping("fecha_inicio", "fechaInicio")
                    .addColumnMapping("fecha_fin", "fechaFin")
                    .addParameter("idVoluntario", idVoluntario)
                    .executeAndFetch(Tarea.class);
            for (Tarea tarea : tareas){
                tarea.setEmergencia(emergenciaRepository.findByTareaId(tarea.getId()));
                tarea.setEstado(estadoRepository.findByTareaId(tarea.getId()));
            }
            return tareas;
        }catch(Exception e){
            throw new QueryException("Tareas no encontradas.\n" + e.getMessage());
        }
    }
}
