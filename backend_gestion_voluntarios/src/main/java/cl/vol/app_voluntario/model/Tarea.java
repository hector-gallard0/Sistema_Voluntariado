package cl.vol.app_voluntario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
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
    private String voluntariosInscritos;

    @ManyToOne
    private Emergencia emergencia;

    @Column(name = "fecha_inicio")
    private Date fechaInicio;

    @Column(name = "fecha_fin")
    private Date fechaFin;

    @ManyToOne
    private EstadoTarea estado;

    @ManyToMany(mappedBy = "tareas")
    private List<Voluntario> voluntarios;
}
