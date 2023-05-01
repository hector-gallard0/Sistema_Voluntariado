package cl.vol.app_voluntario.request;

import cl.vol.app_voluntario.model.Emergencia;
import cl.vol.app_voluntario.model.Estado;
import cl.vol.app_voluntario.model.Habilidad;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateTareaRequest {
    private String nombre;
    private String descripcion;
    private Integer voluntariosRequeridos;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date fechaInicio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date fechaFin;
    private Integer idEmergencia;
}
