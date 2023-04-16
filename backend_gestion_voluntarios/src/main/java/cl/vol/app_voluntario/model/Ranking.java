package cl.vol.app_voluntario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ranking")
public class Ranking implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "id_ranking")
    private int id;

    @Column(name = "calificacion")
    private int puntaje;

    @Column(name = "flag_invitado")
    private int flagInvitado;

    @Column(name = "flag_participa")
    private int flagParticipa;
}
