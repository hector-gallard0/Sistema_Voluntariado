package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.model.Tarea;
import org.springframework.beans.factory.annotation.Autowired;
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
        try (Connection con = sql2o.beginTransaction()) {
            Integer id = con.createQuery("SELECT nextval('tarea_seq')")
                    .executeScalar(Integer.class);
            String sql = "INSERT INTO tarea (id_tarea, nombre, descripcion, cant_vol_requeridos, cant_vol_inscritos, fecha_inicio, fecha_fin, id_emergencia, id_estado) " +
                    "VALUES (:id_tarea, :nombre, :descripcion, :cant_vol_requeridos, :cant_vol_inscritos, :fecha_inicio, :fecha_fin, :id_emergencia, :id_estado)";
            con.createQuery(sql)
                .addColumnMapping("id_tarea", "id")
                .addColumnMapping("cant_vol_requeridos", "voluntariosRequeridos")
                .addColumnMapping("cant_vol_inscritos", "voluntariosInscritos")
                .addColumnMapping("fecha_inicio", "fechaInicio")
                .addColumnMapping("fecha_fin", "fechaFin")
                .addParameter("id_tarea", id)
                .addParameter("nombre", tarea.getNombre())
                .addParameter("descripcion", tarea.getDescripcion())
                .addParameter("cant_vol_requeridos", tarea.getVoluntariosRequeridos())
                .addParameter("cant_vol_inscritos", tarea.getVoluntariosInscritos())
                .addParameter("fecha_inicio", tarea.getFechaInicio())
                .addParameter("fecha_fin", tarea.getFechaFin())
                .addParameter("id_emergencia", tarea.getEmergencia().getId())
                .addParameter("id_estado", tarea.getEstado().getId())
                .executeUpdate();
            con.commit();
            return findById(id);
        }
    }

    @Override
    public Tarea findById(Integer idTarea) {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT t.id_tarea, t.nombre, t.descripcion, t.cant_vol_requeridos, t.cant_vol_inscritos, t.fecha_inicio, t.fecha_fin FROM tarea t WHERE t.id_tarea = :id_tarea";
            Tarea tarea = con.createQuery(sql)
                    .addColumnMapping("id_tarea", "id")
                    .addColumnMapping("cant_vol_requeridos", "voluntariosRequeridos")
                    .addColumnMapping("cant_vol_inscritos", "voluntariosInscritos")
                    .addColumnMapping("fecha_inicio", "fechaInicio")
                    .addColumnMapping("fecha_fin", "fechaFin")
                    .addParameter("id_tarea", idTarea)
                    .executeAndFetchFirst(Tarea.class);
            if (tarea == null) return null;
            tarea.setEmergencia(emergenciaRepository.findByTareaId(tarea.getId()));
            tarea.setEstado(estadoRepository.findByTareaId(tarea.getId()));
            return tarea;
        }
    }

    @Override
    public List<Tarea> findAllByVoluntarioId(Integer idVoluntario) {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT t.id_tarea, t.nombre, t.descripcion, t.cant_vol_requeridos, t.cant_vol_inscritos, t.fecha_inicio, t.fecha_fin " +
                    "FROM tarea t " +
                    "JOIN ranking r ON r.id_voluntario = :id_voluntario AND r.id_tarea = t.id_tarea";
            List<Tarea> tareas = con.createQuery(sql)
                    .addColumnMapping("id_tarea", "id")
                    .addColumnMapping("cant_vol_requeridos", "voluntariosRequeridos")
                    .addColumnMapping("cant_vol_inscritos", "voluntariosInscritos")
                    .addColumnMapping("fecha_inicio", "fechaInicio")
                    .addColumnMapping("fecha_fin", "fechaFin")
                    .addParameter("id_voluntario", idVoluntario)
                    .executeAndFetch(Tarea.class);
            for (Tarea tarea : tareas){
                tarea.setEmergencia(emergenciaRepository.findByTareaId(tarea.getId()));
                tarea.setEstado(estadoRepository.findByTareaId(tarea.getId()));
            }
            return tareas;
        }
    }

    @Override
    public List<Tarea> findAll() {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT id_tarea, nombre, descripcion, cant_vol_requeridos, cant_vol_inscritos, fecha_inicio, fecha_fin FROM tarea";
            List<Tarea> tareas = con.createQuery(sql)
                    .addColumnMapping("id_tarea", "id")
                    .addColumnMapping("cant_vol_requeridos", "voluntariosRequeridos")
                    .addColumnMapping("cant_vol_inscritos", "voluntariosInscritos")
                    .addColumnMapping("fecha_inicio", "fechaInicio")
                    .addColumnMapping("fecha_fin", "fechaFin")
                    .executeAndFetch(Tarea.class);
            for (Tarea tarea : tareas){
                tarea.setEstado(estadoRepository.findByTareaId(tarea.getId()));
            }
            return tareas;
        }
    }
}
