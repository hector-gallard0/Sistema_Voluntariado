package cl.vol.app_voluntario.repository;

import cl.vol.app_voluntario.errors.QueryException;
import cl.vol.app_voluntario.model.Rol;
import cl.vol.app_voluntario.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
                usuario.setRoles(rolRepository.findByUserId(usuario.getId()));
                usuario.setCoordinador(coordinadorRepository.findByUserId(usuario.getId()));
            }
            return usuarios;
        }catch(Exception e){
            throw new QueryException("Usuarios no encontrados.\n" + e.getMessage());
        }
    }

    @Override
    public Usuario findByEmail(String email){
        try (Connection con = sql2o.open()) {
            String sql = "SELECT * FROM usuario WHERE email = :email";
            Usuario usuario = con.createQuery(sql)
                    .addColumnMapping("id_usuario", "id")
                    .addParameter("email", email)
                    .executeAndFetchFirst(Usuario.class);
            usuario.setRoles(rolRepository.findByUserId(usuario.getId()));
            usuario.setCoordinador(coordinadorRepository.findByUserId(usuario.getId()));
            return usuario;
        }
        catch(Exception e){
            throw new QueryException("Usuario no encontrado.\n" + e.getMessage());
        }
    }

    @Override
    public Usuario save(Usuario usuario) {
        try (Connection con = sql2o.open()) {
            Integer id = con.createQuery("SELECT nextval('usuario_seq')")
                    .executeScalar(Integer.class);
            String sql = "INSERT INTO usuario (id_usuario, nombre, apellido, email, password) " +
                    "VALUES (:idUsuario, :nombre, :apellido, :email, :password)";
            con.createQuery(sql)
                    .addColumnMapping("id_usuario", "id")
                    .addParameter("idUsuario", id)
                    .addParameter("nombre", usuario.getNombre())
                    .addParameter("apellido", usuario.getApellido())
                    .addParameter("email", usuario.getEmail())
                    .addParameter("password", usuario.getPassword())
                    .executeUpdate();
            return findById(id);
        }catch(Exception e) {
            throw new QueryException("No se ha podido crear el usuario.\n" + e.getMessage());
        }
    }

    @Override
    public Usuario saveUserRoles(Integer idUsuario, List<Rol> roles) {
        try (Connection con = sql2o.open()) {
            for(Rol rol : roles){
                Integer idUsuarioRol = con.createQuery("SELECT nextval('usuario_rol_seq')")
                        .executeScalar(Integer.class);

                String sql = "INSERT INTO usuario_rol(id_usuario_rol, id_usuario, id_rol)" +
                        "VALUES (:idUsuarioRol, :idUsuario, :idRol)";
                con.createQuery(sql)
                        .addParameter("idUsuarioRol", idUsuarioRol)
                        .addParameter("idUsuario", idUsuario)
                        .addParameter("idRol", rol.getId())
                        .executeUpdate();
            }
            return findById(idUsuario);
        }catch(Exception e) {
            throw new QueryException("No se han podido crear los roles");
        }
    }

    @Override
    public Usuario findByCoordinadorId(Integer idCoordinador) {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT * FROM usuario u JOIN coordinador c ON c.id_usuario = u.id_usuario AND c.id_coordinador = :idCoordinador";
            Usuario usuario = con.createQuery(sql)
                    .addColumnMapping("id_usuario", "id")
                    .addParameter("idCoordinador", idCoordinador)
                    .executeAndFetchFirst(Usuario.class);
            usuario.setRoles(rolRepository.findByUserId(usuario.getId()));
            return usuario;
        } catch (Exception e){
            throw new QueryException("Usuario no encontrado.\n" + e.getMessage());
        }
    }

    @Override
    public Usuario findById(Integer idUsuario) {
        try (Connection con = sql2o.open()) {
            Usuario usuario = null;
            String sql = "SELECT * FROM usuario WHERE id_usuario = :idUsuario";
            usuario = con.createQuery(sql)
                    .addColumnMapping("id_usuario", "id")
                    .addParameter("idUsuario", idUsuario)
                    .executeAndFetchFirst(Usuario.class);
            usuario.setRoles(rolRepository.findByUserId(usuario.getId()));
            usuario.setCoordinador(coordinadorRepository.findByUserId(usuario.getId()));
            return usuario;
        }catch(Exception e){
            throw new QueryException("Usuario no encontrado.\n" + e.getMessage());
        }
    }
}
