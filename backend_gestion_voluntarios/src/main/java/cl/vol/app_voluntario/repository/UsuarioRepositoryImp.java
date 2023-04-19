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

    @Autowired
    private RolRepository rolRepository;

    @Override
    public List<Usuario> findAll(){
        List<Usuario> usuarios = null;
        try (Connection con = sql2o.open()) {
            String sql = "SELECT * FROM usuario";
            usuarios = con.createQuery(sql)
                    .addColumnMapping("id_usuario", "id")
                    .executeAndFetch(Usuario.class);
            for (Usuario usuario : usuarios) {
                List<Rol> roles = rolRepository.findRolesByUserId(usuario.getId());
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
                    .addColumnMapping("id_usuario", "id")
                    .addParameter("email", email)
                    .executeAndFetchFirst(Usuario.class);
            List<Rol> roles = rolRepository.findRolesByUserId(usuario.getId());
            usuario.setRoles(roles);
        }
        return usuario;
    }

    @Override
    public boolean save(Usuario usuario) {
        try (Connection con = sql2o.open()) {
            Integer id = con.createQuery("SELECT nextval('usuario_seq')")
                    .executeScalar(Integer.class);

            String sql = "INSERT INTO usuario (id_usuario, nombre, apellido, email, password) " +
                    "VALUES (:id_usuario, :nombre, :apellido, :email, :password)";
            con.createQuery(sql)
                    .addColumnMapping("id_usuario", "id")
                    .addParameter("id_usuario", id)
                    .addParameter("nombre", usuario.getNombre())
                    .addParameter("apellido", usuario.getApellido())
                    .addParameter("email", usuario.getEmail())
                    .addParameter("password", usuario.getPassword())
                    .executeUpdate();
            sql = "INSERT INTO usuario_rol (id_usuario, id_rol)" +
                    "VALUES (:id_usuario, :id_rol)";
            con.createQuery(sql)
                    .addColumnMapping()
            return true;
        }catch(Error e) {
            return false;
        }
    }

    @Override
    public Usuario findById(int id) {
        Usuario usuario = null;
        try (Connection con = sql2o.open()) {
            String sql = "SELECT * FROM usuario WHERE id_usuario = :id_usuario";
            usuario = con.createQuery(sql)
                    .addColumnMapping("id_usuario", "id")
                    .addParameter("id_usuario", id)
                    .executeAndFetchFirst(Usuario.class);
            List<Rol> roles = rolRepository.findRolesByUserId(usuario.getId());
            usuario.setRoles(roles);
        }
        return usuario;
    }
}
