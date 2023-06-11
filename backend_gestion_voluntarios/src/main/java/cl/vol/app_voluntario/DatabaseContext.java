package cl.vol.app_voluntario;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.sql2o.Sql2o;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Configuration
@EnableTransactionManagement
public class DatabaseContext implements TransactionManagementConfigurer {
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/voluntariodb");
        dataSource.setUsername("postgres");
        dataSource.setPassword("123");

//        // Establecer la variable de sesión
//        try (Connection connection = dataSource.getConnection()) {
//            String query = "SET LOCAL var.logged_user = ?";
//            try (PreparedStatement statement = connection.prepareStatement(query)) {
//                // Obtener el usuario autenticado y establecerlo como valor de la variable de sesión
//                System.out.println("preparedstatement");
//                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//                String loggedUser = authentication.getName();
//                statement.setString(1, loggedUser);
//                statement.execute();
//            }
//        } catch (SQLException e) {
//            // Manejo de excepciones
//        }

        return dataSource;
    }

    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public Sql2o sql2o() {
        return new Sql2o(dataSource());
    }

}

