package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.model.Rol;
import cl.vol.app_voluntario.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Query;


import java.util.List;

@Repository
public class UsuarioRepositoryImp implements UsuarioRepository{

    @Autowired
    private Sql2o sql2o;

    @Override
    public List<Usuario> findAll(){
        List<Usuario> usuarios = null;
        try (Connection con = sql2o.open()) {
            String sql = "SELECT * FROM usuario";
            usuarios = con.createQuery(sql)
                    .addColumnMapping("id_usuario", "id")
                    .executeAndFetch(Usuario.class);
            for (Usuario usuario : usuarios) {
                String rolSql = "SELECT r.* FROM rol r INNER JOIN usuario_rol ur ON r.id_rol = ur.id_rol WHERE ur.id_usuario = :id_usuario";
                List<Rol> roles = con.createQuery(rolSql)
                        .addColumnMapping("id_rol", "id")
                        .addParameter("id_usuario", usuario.getId())
                        .executeAndFetch(Rol.class);
                usuario.setRoles(roles);
            }
        }
        return usuarios;
    }


    @Override
    public Usuario findByEmail(String email){
        Usuario usuario = null;
        try (Connection con = sql2o.open()) {
            String sql = "SELECT * FROM usuario WHERE email = :email";
            usuario = con.createQuery(sql)
                    .addParameter("email", email)
                    .executeAndFetchFirst(Usuario.class);
        }
        return usuario;
    }

    @Override
    public void save(Usuario usuario) {
        try (Connection con = sql2o.open()) {
            String sql = "INSERT INTO usuario (nombre, apellido, email, password) " +
                    "VALUES (:nombre, :apellido, :email)";
            con.createQuery(sql)
                    .addParameter("nombre", usuario.getNombre())
                    .addParameter("apellido", usuario.getApellido())
                    .addParameter("email", usuario.getEmail())
                    .addParameter("password", usuario.getPassword())
                    .executeUpdate();
        }
    }
}
