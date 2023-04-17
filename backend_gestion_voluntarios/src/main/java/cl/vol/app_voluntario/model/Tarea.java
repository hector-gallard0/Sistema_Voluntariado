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
@Entity
@Table(name = "tarea")
public class Tarea {

    @Id
    @GeneratedValue
    @Column(name = "id_tarea")
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "cant_vol_requeridos")
    private int voluntariosRequeridos;

    @Column(name = "cant_vol_inscritos")
    private int voluntariosInscritos;

    @Column(name = "fecha_inicio")
    private Date fechaInicio;

    @Column(name = "fecha_fin")
    private Date fechaFin;

    @OneToOne(mappedBy = "tarea", cascade = CascadeType.ALL)
    private Estado estado;

    @ManyToMany
    @JoinTable(
            name = "tarea_habilidad",
            joinColumns = @JoinColumn(name = "id_tarea"),
            inverseJoinColumns = @JoinColumn(name = "id_eme_habilidad")
    )
    private List<EmeHabilidad> emeHabilidades = new ArrayList<>();
}
