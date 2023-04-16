package cl.vol.app_voluntario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "voluntario")
public class Voluntario {

    @Id
    @GeneratedValue
    @Column(name = "id_voluntario")
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "ranking",
            joinColumns = {@JoinColumn(name = "id_voluntario")},
            inverseJoinColumns = {@JoinColumn(name = "id_tarea")})
    private List<Tarea> tareas;

    // Constructor, getters y setters
}
