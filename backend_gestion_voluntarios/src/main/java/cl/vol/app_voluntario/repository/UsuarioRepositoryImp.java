package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.errors.QueryException;
import cl.vol.app_voluntario.model.Rol;
import cl.vol.app_voluntario.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;


import java.util.List;

@Repository
public class UsuarioRepositoryImp implements UsuarioRepository{
    @Autowired
    private Sql2o sql2o;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private CoordinadorRepository coordinadorRepository;

    @Override
    public List<Usuario> findAll(){
        try (Connection con = sql2o.open()) {
            String sql = "SELECT * FROM usuario";
            List<Usuario> usuarios = con.createQuery(sql)
                    .addColumnMapping("id_usuario", "id")
                    .executeAndFetch(Usuario.class);
            for (Usuario usuario : usuarios) {
                usuario.setRoles(rolRepository.findAllByUserId(usuario.getId()));
                usuario.setCoordinador(coordinadorRepository.findByUserId(usuario.getId()));
            }
            return usuarios;
        }
    }

    @Override
    public Usuario findByEmail(String email){
        try (Connection con = sql2o.open()) {
            String sql = "SELECT id_usuario, nombre, apellido, password FROM usuario WHERE email = :email";
            Usuario usuario = con.createQuery(sql)
                    .addColumnMapping("id_usuario", "id")
                    .addParameter("email", email)
                    .executeAndFetchFirst(Usuario.class);
            if(usuario == null) return null;
            usuario.setRoles(rolRepository.findAllByUserId(usuario.getId()));
            usuario.setCoordinador(coordinadorRepository.findByUserId(usuario.getId()));
            return usuario;
        }
    }

    @Override
    public Usuario save(Usuario usuario) {
        try (Connection con = sql2o.beginTransaction()) {
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
            con.commit();
            return findById(id);
        }
    }

    @Override
    public Usuario saveUserRoles(Integer idUsuario, List<Rol> roles) {
        try (Connection con = sql2o.beginTransaction()) {
            for(Rol rol : roles){
                Integer idUsuarioRol = con.createQuery("SELECT nextval('usuario_rol_seq')")
                        .executeScalar(Integer.class);
                String sql = "INSERT INTO usuario_rol(id_usuario_rol, id_usuario, id_rol)" +
                        "VALUES (:id_usuario_rol, :id_usuario, :id_rol)";
                con.createQuery(sql)
                    .addParameter("id_usuario_rol", idUsuarioRol)
                    .addParameter("id_usuario", idUsuario)
                    .addParameter("id_rol", rol.getId())
                    .executeUpdate();
            }
            con.commit();
            return findById(idUsuario);
        }catch(Exception e) {
            throw new QueryException("No se han podido crear los roles");
        }
    }

    @Override
    public Usuario findByCoordinadorId(Integer idCoordinador) {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT u.* FROM usuario u JOIN coordinador c ON c.id_usuario = u.id_usuario AND c.id_coordinador = :id_coordinador";
            Usuario usuario = con.createQuery(sql)
                    .addColumnMapping("id_usuario", "id")
                    .addParameter("id_coordinador", idCoordinador)
                    .executeAndFetchFirst(Usuario.class);
            if(usuario == null) return null;
            usuario.setRoles(rolRepository.findAllByUserId(usuario.getId()));
            return usuario;
        }
    }

    @Override
    public Usuario findById(Integer idUsuario) {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT * FROM usuario WHERE id_usuario = :id_usuario";
            Usuario usuario = con.createQuery(sql)
                    .addColumnMapping("id_usuario", "id")
                    .addParameter("id_usuario", idUsuario)
                    .executeAndFetchFirst(Usuario.class);
            if(usuario == null) return null;
            usuario.setRoles(rolRepository.findAllByUserId(usuario.getId()));
            usuario.setCoordinador(coordinadorRepository.findByUserId(usuario.getId()));
            return usuario;
        }
    }
}
