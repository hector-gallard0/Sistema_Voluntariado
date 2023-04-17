package cl.vol.app_voluntario.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class Emergencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_emergencia")
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha_inicio")
    private Date fechaInicio;

    @Column(name = "fecha_fin")
    private Date fechaFin;

    @ManyToOne
    @JoinColumn(name = "id_institucion")
    @JsonBackReference
    private Institucion institucion;

    @ManyToMany
    @JoinTable(
            name = "eme_habilidad",
            joinColumns = @JoinColumn(name = "id_emergencia"),
            inverseJoinColumns = @JoinColumn(name = "id_habilidad")
    )
    private List<Habilidad> habilidades = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Tarea> tareas = new ArrayList<>();
}
