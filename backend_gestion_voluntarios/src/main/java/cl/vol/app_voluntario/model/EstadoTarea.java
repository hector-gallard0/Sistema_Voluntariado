package cl.vol.app_voluntario.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "estado_tarea")
public class EstadoTarea {
    @Id
    @GeneratedValue
    @Column(name = "id_estado_tarea")
    private int id;

    @Column(name = "descripcion")
    private String descripcion;
}
