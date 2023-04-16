package cl.vol.app_voluntario.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Habilidad {
    @Id
    @GeneratedValue
    @Column(name = "id_habilidad")
    private int id;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToMany(mappedBy = "habilidades")
    @JsonBackReference
    private List<Emergencia> emergencias = new ArrayList<>();
}
