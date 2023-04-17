package cl.vol.app_voluntario.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "eme_habilidad")
public class EmeHabilidad {
    @Id
    @GeneratedValue
    @Column(name = "id_eme_habilidad")
    private int id_eme_habilidad;

    @ManyToOne
    @JoinColumn(name = "id_habilidad")
    @JsonManagedReference
    private Habilidad habilidad;
}
