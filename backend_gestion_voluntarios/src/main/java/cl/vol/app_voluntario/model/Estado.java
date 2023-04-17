package cl.vol.app_voluntario.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "estado")
public class Estado {
    @Id
    @GeneratedValue
    @Column(name = "id_estado")
    private int id;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToOne
    @JoinColumn(name = "id_tarea")
    private Tarea tarea;
}
