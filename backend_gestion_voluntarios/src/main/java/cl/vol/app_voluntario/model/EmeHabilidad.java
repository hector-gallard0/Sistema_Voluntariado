package cl.vol.app_voluntario.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmeHabilidad {
    private int id_eme_habilidad;
    private Habilidad habilidad;
}
