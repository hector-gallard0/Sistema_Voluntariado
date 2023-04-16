package cl.vol.app_voluntario.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "coordinador")
@AllArgsConstructor
@NoArgsConstructor
public class Coordinador {
    @Id
    @GeneratedValue
    @Column(name = "id_coordinador")
    private int id;

    @OneToOne(mappedBy = "coordinador", cascade = CascadeType.ALL)
    private Usuario usuario;

    @OneToOne(mappedBy = "coordinador", cascade = CascadeType.ALL)
    private Institucion institucion;
}
