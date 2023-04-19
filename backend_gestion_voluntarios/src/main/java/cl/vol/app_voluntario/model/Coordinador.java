package cl.vol.app_voluntario.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coordinador {
    private Integer id;
    private Institucion institucion;
    private Usuario usuario;
}
