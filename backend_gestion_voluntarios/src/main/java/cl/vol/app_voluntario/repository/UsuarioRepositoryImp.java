package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.model.Coordinador;
import cl.vol.app_voluntario.model.Rol;
import cl.vol.app_voluntario.model.Usuario;
import cl.vol.app_voluntario.model.Voluntario;
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
    @Autowired
    private CoordinadorRepository coordinadorRepository;

    @Override
    public List<Usuario> findAll(){
        List<Usuario> usuarios = null;
        try (Connection con = sql2o.open()) {
            String sql = "SELECT * FROM usuario";
            usuarios = con.createQuery(sql)
                    .addColumnMapping("id_usuario", "id")
                    .executeAndFetch(Usuario.class);
            for (Usuario usuario : usuarios) {
                usuario.setRoles(findRolesByUserId(usuario.getId()));
                usuario.setCoordinador(findCoordinadorByUserId(usuario.getId()));
            }
        }
        return usuarios;
    }
    private Coordinador findCoordinadorByUserId(Integer id_usuario) {
        try (Connection con = sql2o.open()) {
            Coordinador coordinador = null;
            String sql = "SELECT c.id_coordinador FROM coordinador c WHERE c.id_usuario = :id_usuario";
            coordinador = con.createQuery(sql)
                    .addColumnMapping("id_coordinador", "id")
                    .addParameter("id_usuario", id_usuario)
                    .executeAndFetchFirst(Coordinador.class);
            if(coordinador == null) return null;
            coordinador.setInstitucion(coordinadorRepository.findInstitucionByCoordinadorId(coordinador.getId()));
            return coordinador;
        }
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

            if(usuario == null){
                return null;
            }

            List<Rol> roles = findRolesByUserId(usuario.getId());
            usuario.setRoles(roles);
            return usuario;
        }
        catch(Error e){
            return null;
        }
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
            return true;
        }catch(Error e) {
            return false;
        }
    }

    @Override
    public boolean saveUserRoles(int id_usuario, List<Rol> roles) {
        try (Connection con = sql2o.open()) {
            for(Rol rol : roles){
                Integer id_usuario_rol = con.createQuery("SELECT nextval('usuario_rol_seq')")
                        .executeScalar(Integer.class);

                String sql = "INSERT INTO usuario_rol(id_usuario_rol, id_usuario, id_rol)" +
                        "VALUES (:id_usuario_rol, :id_usuario, :id_rol)";
                con.createQuery(sql)
                        .addParameter("id_usuario_rol", id_usuario_rol)
                        .addParameter("id_usuario", id_usuario)
                        .addParameter("id_rol", rol.getId())
                        .executeUpdate();
            }
            return true;
        }catch(Error e) {
            return false;
        }
    }

    @Override
    public List<Rol> findRolesByUserId(int id_usuario) {
        try(Connection con = sql2o.open()){
            String rolSql = "SELECT r.* FROM rol r INNER JOIN usuario_rol ur ON r.id_rol = ur.id_rol WHERE ur.id_usuario = :id_usuario";
            return con.createQuery(rolSql)
                    .addColumnMapping("id_rol", "id")
                    .addParameter("id_usuario", id_usuario)
                    .executeAndFetch(Rol.class);
        }catch (Error e){
            return null;
        }
    }

    @Override
    public Usuario findById(int id) {
        try (Connection con = sql2o.open()) {
            Usuario usuario = null;
            String sql = "SELECT * FROM usuario WHERE id_usuario = :id_usuario";
            usuario = con.createQuery(sql)
                    .addColumnMapping("id_usuario", "id")
                    .addParameter("id_usuario", id)
                    .executeAndFetchFirst(Usuario.class);
            List<Rol> roles = findRolesByUserId(usuario.getId());
            usuario.setRoles(roles);
            return usuario;
        }catch(Error e){
            return null;
        }
    }
}
