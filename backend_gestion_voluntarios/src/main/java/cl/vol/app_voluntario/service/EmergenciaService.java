package cl.vol.app_voluntario.service;

import cl.vol.app_voluntario.errors.ApiErrorException;
import cl.vol.app_voluntario.errors.InstitucionNotFoundException;
import cl.vol.app_voluntario.errors.InvalidDatesException;
import cl.vol.app_voluntario.model.Emergencia;
import cl.vol.app_voluntario.model.Habilidad;
import cl.vol.app_voluntario.model.Institucion;
import cl.vol.app_voluntario.repository.EmergenciaRepository;
import cl.vol.app_voluntario.repository.HabilidadRepository;
import cl.vol.app_voluntario.repository.InstitucionRepository;
import cl.vol.app_voluntario.request.CreateEmergenciaRequest;
import cl.vol.app_voluntario.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmergenciaService {
    private final EmergenciaRepository emergenciaRepository;
    private final InstitucionRepository institucionRepository;
    private final HabilidadRepository habilidadRepository;
    public ResponseEntity<Emergencia> createEmergencia(CreateEmergenciaRequest request){
        if(!ValidationUtil.validateDates(request.getFechaInicio(), request.getFechaFin())){
            throw new InvalidDatesException("La fecha de inicio debe ser menor a la fecha final");
        };
        Institucion institucion = institucionRepository.findById(request.getId_institucion());
        if(institucion == null) throw new ApiErrorException("La institución no existe.\n");
        Emergencia emergencia = new Emergencia();
        emergencia.setNombre(request.getNombre());
        emergencia.setDescripcion(request.getDescripcion());
        emergencia.setFechaInicio(request.getFechaInicio());
        emergencia.setFechaFin(request.getFechaFin());
        emergencia.setInstitucion(institucion);
        return new ResponseEntity<>(emergenciaRepository.save(emergencia), HttpStatus.CREATED);
    }

    public List<Emergencia> getEmergencias(){
        return emergenciaRepository.findAll();
    }

    public List<Habilidad> getHabilidades(Integer idEmergencia) { return habilidadRepository.findAllByEmergenciaId(idEmergencia); }
}
