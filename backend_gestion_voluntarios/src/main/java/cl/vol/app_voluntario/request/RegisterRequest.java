package cl.vol.app_voluntario.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Debe ingresar un nombre.")
    private String nombre;
    @NotBlank(message = "Debe ingresar un apellido.")
    private String apellido;
    @NotBlank(message = "Debe ingresar un email.")
    @Email(message = "El email ingresado no es válido.")
    private String email;
    @NotBlank(message = "Debe ingresar una contraseña.")
    private String password;
    @NotNull(message = "Debe ingresar una institución.")
    private Integer id_institucion;
    @NotNull(message = "Debe ingresar si el usuario es voluntario o no.")
    private boolean voluntario;
    @NotNull(message = "Debe ingresar si el usuario es coordinador o no.")
    private boolean coordinador;
}
