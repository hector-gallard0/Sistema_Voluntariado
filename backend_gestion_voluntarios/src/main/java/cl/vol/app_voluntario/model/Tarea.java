package cl.vol.app_voluntario.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tarea {
    private int id;
    private String nombre;
    private String descripcion;
    private int voluntariosRequeridos;
    private int voluntariosInscritos;
    private Date fechaInicio;
    private Date fechaFin;
    private Estado estado;
    private List<EmeHabilidad> emeHabilidades = new ArrayList<>();
}
