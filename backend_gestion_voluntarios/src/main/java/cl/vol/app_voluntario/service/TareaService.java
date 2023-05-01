package cl.vol.app_voluntario.service;

import cl.vol.app_voluntario.errors.ApiErrorException;
import cl.vol.app_voluntario.errors.InvalidDatesException;
import cl.vol.app_voluntario.model.Emergencia;
import cl.vol.app_voluntario.model.Estado;
import cl.vol.app_voluntario.model.Tarea;
import cl.vol.app_voluntario.repository.EmergenciaRepository;
import cl.vol.app_voluntario.repository.EstadoRepository;
import cl.vol.app_voluntario.repository.TareaRepository;
import cl.vol.app_voluntario.request.CreateTareaRequest;
import cl.vol.app_voluntario.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TareaService {
    private final TareaRepository tareaRepository;
    private final EmergenciaRepository emergenciaRepository;
    private final EstadoRepository estadoRepository;

    public void createTarea(CreateTareaRequest request){
        if(!ValidationUtil.validateDates(request.getFechaInicio(), request.getFechaFin())) throw new ApiErrorException("La fecha de inicio debe ser menor a la fecha final");
        Emergencia emergencia = emergenciaRepository.findById(request.getIdEmergencia());
        if(emergencia == null) throw new ApiErrorException("Emergencia no encontrada.");
        Estado estado = estadoRepository.findById(0);
        if(estado == null) throw new ApiErrorException("Estado no encontrado.");
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
        tareaRepository.save(tarea);
    }

}
