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
@Entity
@Table(name = "voluntario")
public class Voluntario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_voluntario")
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @JsonBackReference
    private Usuario usuario;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "ranking",
            joinColumns = {@JoinColumn(name = "id_voluntario")},
            inverseJoinColumns = {@JoinColumn(name = "id_tarea")})
    private List<Tarea> tareas;

    @ManyToMany
    @JoinTable(
            name = "vol_habilidad",
            joinColumns = @JoinColumn(name = "id_voluntario"),
            inverseJoinColumns = @JoinColumn(name = "id_habilidad")
    )
    private List<Habilidad> habilidades = new ArrayList<>();
}
