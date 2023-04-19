package cl.vol.app_voluntario.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Voluntario {
    private Integer id;
    private Usuario usuario;
    private List<Tarea> tareas;
    private List<Habilidad> habilidades = new ArrayList<>();
}
