package cl.vol.app_voluntario.service;

import cl.vol.app_voluntario.model.Emergencia;
import cl.vol.app_voluntario.model.Estado;
import cl.vol.app_voluntario.model.Tarea;
import cl.vol.app_voluntario.repository.EmergenciaRepository;
import cl.vol.app_voluntario.repository.EstadoRepository;
import cl.vol.app_voluntario.repository.TareaRepository;
import cl.vol.app_voluntario.request.CreateTareaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TareaService {
    private final TareaRepository tareaRepository;
    private final EmergenciaRepository emergenciaRepository;
    private final EstadoRepository estadoRepository;

    public Tarea createTarea(CreateTareaRequest request){
        Emergencia emergencia = emergenciaRepository.findById(request.getId_emergencia());
        Estado estado = estadoRepository.findById(request.getId_estado());
        Tarea tarea = new Tarea();
        tarea.setNombre(request.getNombre());
        tarea.setDescripcion(request.getDescripcion());
        tarea.setVoluntariosRequeridos(request.getVoluntariosRequeridos());
        tarea.setVoluntariosInscritos(request.getVoluntariosInscritos());
        tarea.setFechaInicio(request.getFechaInicio());
        tarea.setFechaFin(request.getFechaFin());
        tarea.setEmergencia(emergencia);
        tarea.setEstado(estado);
        return tareaRepository.save(tarea);
    }

}
