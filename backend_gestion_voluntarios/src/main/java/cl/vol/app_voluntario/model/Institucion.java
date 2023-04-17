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
@Entity
@Table(name = "institucion")
@AllArgsConstructor
@NoArgsConstructor
public class Institucion {
    @Id
    @GeneratedValue
    @Column(name = "id_institucion")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "institucion", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Coordinador> coordinadores = new ArrayList<>();

    @OneToMany(mappedBy = "institucion", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Emergencia> emergencias = new ArrayList<>();

}
