package cl.vol.app_voluntario.service;

import cl.vol.app_voluntario.errors.ApiErrorException;
import cl.vol.app_voluntario.errors.InvalidDatesException;
import cl.vol.app_voluntario.model.Emergencia;
import cl.vol.app_voluntario.model.Estado;
import cl.vol.app_voluntario.model.Habilidad;
import cl.vol.app_voluntario.model.Tarea;
import cl.vol.app_voluntario.repository.EmergenciaRepository;
import cl.vol.app_voluntario.repository.EstadoRepository;
import cl.vol.app_voluntario.repository.HabilidadRepository;
import cl.vol.app_voluntario.repository.TareaRepository;
import cl.vol.app_voluntario.request.CreateTareaRequest;
import cl.vol.app_voluntario.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TareaService {
    private final TareaRepository tareaRepository;
    private final EmergenciaRepository emergenciaRepository;
    private final EstadoRepository estadoRepository;
    private final HabilidadRepository habilidadRepository;
    public void createTarea(CreateTareaRequest request){
        try{

            if(!ValidationUtil.validateDates(request.getFechaInicio(), request.getFechaFin())) throw new ApiErrorException("La fecha de inicio debe ser menor a la fecha final");
            Emergencia emergencia = emergenciaRepository.findById(request.getIdEmergencia());

            if(emergencia == null) throw new ApiErrorException("Emergencia no encontrada.");
            Estado estado = estadoRepository.findById(0);

            if(estado == null) throw new ApiErrorException("Estado no encontrado.");
            List<Habilidad> habilidades = new ArrayList<>();

            for(Integer id : request.getIdsHabilidades()){
                Habilidad habilidad = habilidadRepository.findById(id);
                if(habilidad == null) throw new ApiErrorException("Una habilidad no existe");
                if(!sameEmergencia(emergencia.getId(), habilidad.getId())) throw new ApiErrorException("Una habilidad escogida no es parte de la emergencia.");
                habilidades.add(habilidad);
            }

            Tarea tarea = new Tarea();
            tarea.setNombre(request.getNombre());
            if(request.getDescripcion().length() > 300) throw new ApiErrorException("La descripción puede ser de máximo 300 caracteres.");
            tarea.setDescripcion(request.getDescripcion());
            tarea.setVoluntariosRequeridos(request.getVoluntariosRequeridos());
            tarea.setVoluntariosInscritos(0);
            tarea.setFechaInicio(request.getFechaInicio());
            tarea.setFechaFin(request.getFechaFin());
            tarea.setEmergencia(emergencia);
            tarea.setEstado(estado);
            tarea.setHabilidades(habilidades);
            tarea.setLongit(request.getLongit());
            tarea.setLatit(request.getLatit());
            Tarea resultadoTarea = tareaRepository.save(tarea);
            tareaRepository.saveTareaHabilidad(resultadoTarea.getId(), resultadoTarea.getHabilidades());
        }catch(Exception e){
            throw new ApiErrorException("Error al crear la tarea " + e.getMessage());
        }
    }

    private boolean sameEmergencia(Integer idEmergencia, Integer idHabilidad){
        for(Emergencia em : emergenciaRepository.findAllByHabilidadId(idHabilidad)){
            if(em.getId().equals(idEmergencia)) return true;
        }
        return false;
    }

    public List<Tarea> getTareas() {
        return tareaRepository.findAll();
    }

    public Tarea getTarea(Integer id) {
        try{
            return tareaRepository.findById(id);
        }catch(Exception e){
            throw new ApiErrorException("Tarea no encontrada.");
        }
    }

    public void updateTarea(Integer id, CreateTareaRequest newTarea){
        try{
            Tarea tarea = tareaRepository.findById(id);
            if(tarea == null) throw new ApiErrorException("La tarea a actualizar no existe");
            if(newTarea.getNombre() != null) {
                tarea.setNombre(newTarea.getNombre());
            }
            if(newTarea.getDescripcion() != null){
                tarea.setDescripcion(newTarea.getDescripcion());
            }
            if(newTarea.getFechaInicio() != null){
                tarea.setFechaInicio(newTarea.getFechaInicio());
            }
            if(newTarea.getFechaFin() != null){
                tarea.setFechaFin(newTarea.getFechaFin());
            }
            if(newTarea.getVoluntariosInscritos() != null){
                tarea.setVoluntariosInscritos(newTarea.getVoluntariosInscritos());
            }
            if(newTarea.getVoluntariosRequeridos() != null){
                tarea.setVoluntariosInscritos(newTarea.getVoluntariosRequeridos());
            }
            if(newTarea.getIdEmergencia() != null){
                tarea.setEmergencia(emergenciaRepository.findById(newTarea.getIdEmergencia()));
            }
            if(newTarea.getIdEstado() != null){
                tarea.setEstado(estadoRepository.findById(newTarea.getIdEstado()));
            }
            if(newTarea.getLongit() != null){
                tarea.setLongit(newTarea.getLongit());
            }
            if(newTarea.getLatit() != null){
                tarea.setLatit(newTarea.getLatit());
            }
            tareaRepository.set(tarea);
        }catch(Exception e){
            throw new ApiErrorException("Error al actualizar la tarea." + e.getMessage());
        }
    }

    public void deleteTarea(Integer id) {
        Tarea tarea = tareaRepository.findById(id);
        if(tarea != null){
            tareaRepository.deleteTareaHabilidad(id);
            tareaRepository.delete(id);
        }
    }
}
