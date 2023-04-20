package cl.vol.app_voluntario.service;

import cl.vol.app_voluntario.model.Emergencia;
import cl.vol.app_voluntario.model.Institucion;
import cl.vol.app_voluntario.repository.EmergenciaRepository;
import cl.vol.app_voluntario.repository.InstitucionRepository;
import cl.vol.app_voluntario.request.CreateEmergenciaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmergenciaService {
    private final EmergenciaRepository emergenciaRepository;
    private final InstitucionRepository institucionRepository;
    public Emergencia createEmergencia(CreateEmergenciaRequest request){
        try{
            Institucion institucion = institucionRepository.findById(request.getId_institucion());
            Emergencia emergencia = new Emergencia();
            emergencia.setNombre(request.getNombre());
            emergencia.setDescripcion(request.getDescripcion());
            emergencia.setFechaInicio(request.getFechaInicio());
            emergencia.setFechaFin(request.getFechaFin());
            emergencia.setInstitucion(institucion);
            return emergenciaRepository.save(emergencia);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public List<Emergencia> getEmergencias(){
        return emergenciaRepository.findAll();
    }
}
