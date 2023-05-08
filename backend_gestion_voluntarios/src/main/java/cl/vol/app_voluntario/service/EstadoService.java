package cl.vol.app_voluntario.service;

import cl.vol.app_voluntario.errors.ApiErrorException;
import cl.vol.app_voluntario.model.Estado;
import cl.vol.app_voluntario.repository.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstadoService {
    private final EstadoRepository estadoRepository;

    public List<Estado> getEstados(){
        try{
            return estadoRepository.findAll();
        }catch(Exception e){
            throw new ApiErrorException("Los estados buscados no existen.");
        }
    }
}
