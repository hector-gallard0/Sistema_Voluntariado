package cl.vol.app_voluntario.model;

import cl.vol.app_voluntario.model.Usuario;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "rol")
public class Rol {
    @Id
    @GeneratedValue
    @Column(name = "id_rol")
    private int id;

    @Column(name = "nombre")
    private String nombre;
}
