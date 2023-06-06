package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.model.Emergencia;
import cl.vol.app_voluntario.model.Tarea;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private HabilidadRepository habilidadRepository;
    @Override
    public Emergencia save(Emergencia emergencia) {
        try (Connection con = sql2o.beginTransaction()) {
            Integer id = con.createQuery("SELECT nextval('emergencia_seq')")
                    .executeScalar(Integer.class);

            String sql = "INSERT INTO emergencia (id_emergencia, nombre, descripcion, fecha_inicio, fecha_fin, id_institucion) " +
                    "VALUES (:id_emergencia, :nombre, :descripcion, :fecha_inicio, :fecha_fin, :id_institucion)";
            con.createQuery(sql)
                    .addColumnMapping("id_emergencia", "id")
                    .addColumnMapping("nombre", "nombre")
                    .addColumnMapping("descripcion", "descripcion")
                    .addColumnMapping("fecha_inicio", "fechaInicio")
                    .addColumnMapping("fecha_fin", "fechaFin")
                    .addParameter("id_emergencia", id)
                    .addParameter("nombre", emergencia.getNombre())
                    .addParameter("descripcion", emergencia.getDescripcion())
                    .addParameter("fecha_inicio", emergencia.getFechaInicio())
                    .addParameter("fecha_fin", emergencia.getFechaFin())
                    .addParameter("id_institucion", emergencia.getInstitucion().getId())
                    .executeUpdate()
                    .getResult();
            con.commit();
            return findById(id);
        }
    }

    @Override
    public Emergencia findById(Integer idEmergencia) {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT e.id_emergencia, e.nombre, e.descripcion, e.fecha_inicio, e.fecha_fin FROM emergencia e WHERE e.id_emergencia = :id_emergencia";
            Emergencia emergencia = con.createQuery(sql)
                    .addColumnMapping("id_emergencia", "id")
                    .addColumnMapping("fecha_inicio", "fechaInicio")
                    .addColumnMapping("fecha_fin", "fechaFin")
                    .addParameter("id_emergencia", idEmergencia)
                    .executeAndFetchFirst(Emergencia.class);
            if(emergencia == null) return null;
            emergencia.setInstitucion(institucionRepository.findByEmergenciaId(idEmergencia));
            return emergencia;
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
                emergencia.setHabilidades(habilidadRepository.findAllByEmergenciaId(emergencia.getId()));
            }
            return emergencias;
        }
    }

    @Override
    public Emergencia findByTareaId(Integer idTarea) {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT e.id_emergencia, e.nombre, e.descripcion, e.fecha_inicio, e.fecha_fin FROM emergencia e JOIN tarea t ON t.id_emergencia = e.id_emergencia AND t.id_tarea = :id_tarea";
            Emergencia emergencia = con.createQuery(sql)
                    .addColumnMapping("id_emergencia", "id" )
                    .addColumnMapping("fecha_inicio", "fechaInicio")
                    .addColumnMapping("fecha_fin", "fechaFin")
                    .addParameter("id_tarea", idTarea)
                    .executeAndFetchFirst(Emergencia.class);
            if(emergencia == null) return null;
            //ACA FALTAN LAS HABILIDADES Y TAREAS
            emergencia.setInstitucion(institucionRepository.findByEmergenciaId(emergencia.getId()));
            return emergencia;
        }
    }

    @Override
    public Integer findEmeHabilidadIdByHabilidadId(Integer idHabilidad){
        try (Connection con = sql2o.open()) {
            String sql = "SELECT eh.id_eme_habilidad FROM eme_habilidad eh WHERE eh.id_habilidad = :id_habilidad";
            return con.createQuery(sql)
                    .addParameter("id_habilidad", idHabilidad)
                    .executeScalar(Integer.class);
        }
    }

    @Override
    public List<Emergencia> findAllByHabilidadId(Integer idHabilidad){
        try (Connection con = sql2o.open()) {
            String sql = "SELECT e.id_emergencia, e.nombre, e.descripcion, e.fecha_inicio, e.fecha_fin " +
                    "FROM emergencia e " +
                    "JOIN eme_habilidad eh ON e.id_emergencia = eh.id_emergencia AND eh.id_habilidad = :id_habilidad";
            List<Emergencia> emergencias = con.createQuery(sql)
                    .addColumnMapping("id_emergencia", "id" )
                    .addColumnMapping("fecha_inicio", "fechaInicio")
                    .addColumnMapping("fecha_fin", "fechaFin")
                    .addParameter("id_habilidad", idHabilidad)
                    .executeAndFetch(Emergencia.class);
            for (Emergencia emergencia : emergencias) {
                emergencia.setInstitucion(institucionRepository.findByEmergenciaId(emergencia.getId()));
                emergencia.setHabilidades(habilidadRepository.findAllByEmergenciaId(emergencia.getId()));
            }
            return emergencias;
        }
    }

    @Override
    public void set(Emergencia emergencia){
        try (Connection con = sql2o.beginTransaction()) {
            String sql = "UPDATE emergencia " +
                    "SET descripcion = :descripcion, " +
                    "nombre = :nombre, " +
                    "fecha_inicio = :fecha_inicio, " +
                    "fecha_fin = :fecha_fin, " +
                    "id_institucion = :id_institucion " +
                    "WHERE id_emergencia = :id_emergencia";
            con.createQuery(sql)
                    .addColumnMapping("id_emergencia", "id")
                    .addColumnMapping("fecha_inicio", "fechaInicio")
                    .addColumnMapping("fec.ha_fin", "fechaFin")
                    .addParameter("id_emergencia", emergencia.getId())
                    .addParameter("nombre", emergencia.getNombre())
                    .addParameter("descripcion", emergencia.getDescripcion())
                    .addParameter("fecha_inicio", emergencia.getFechaInicio())
                    .addParameter("fecha_fin", emergencia.getFechaFin())
                    .addParameter("id_institucion", emergencia.getInstitucion().getId())
                    .executeUpdate();
            con.commit();
        }
    }
}
