package cl.vol.app_voluntario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ranking implements Serializable {
    private int id;
    private int puntaje;
    private int flagInvitado;
    private int flagParticipa;
}
