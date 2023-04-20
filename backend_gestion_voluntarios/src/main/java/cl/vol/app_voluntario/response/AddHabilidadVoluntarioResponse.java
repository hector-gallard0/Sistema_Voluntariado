package cl.vol.app_voluntario.response;


import cl.vol.app_voluntario.model.Habilidad;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddHabilidadVoluntarioResponse {
    private Habilidad habilidad;
    private String message;
}
