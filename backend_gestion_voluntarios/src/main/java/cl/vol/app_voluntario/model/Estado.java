package cl.vol.app_voluntario.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Estado {
    private int id;
    private String descripcion;
    private Tarea tarea;
}
