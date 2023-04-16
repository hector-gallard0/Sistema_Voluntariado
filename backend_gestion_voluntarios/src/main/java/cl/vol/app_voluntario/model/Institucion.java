package cl.vol.app_voluntario.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "institucion")
@AllArgsConstructor
@NoArgsConstructor
public class Institucion {
    @Id
    @GeneratedValue
    @Column(name = "id_institucion")
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToOne
    @JoinColumn(name = "id_coordinador")
    Coordinador coordinador;

    @OneToMany(mappedBy = "institucion")
    private List<Emergencia> emergencias = new ArrayList<>();

}
