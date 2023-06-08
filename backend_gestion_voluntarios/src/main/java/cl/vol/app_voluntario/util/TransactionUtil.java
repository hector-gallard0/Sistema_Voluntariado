package cl.vol.app_voluntario.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.sql2o.Connection;

public class TransactionUtil {
    public static void createTempTableWithUsername(Connection con){
        con.createQuery("CREATE TEMPORARY TABLE temp_params(usuario varchar);")
                .executeUpdate();
        con.createQuery("INSERT INTO temp_params(usuario) " +
                        "VALUES (:usuario)")
                .addParameter("usuario", SecurityContextHolder.getContext().getAuthentication().getName())
                .executeUpdate();
    }
}
