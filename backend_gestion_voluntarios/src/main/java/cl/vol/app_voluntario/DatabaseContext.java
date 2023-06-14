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
    public DataSource dataSource() throws SQLException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
<<<<<<< Updated upstream
        dataSource.setUrl("jdbc:postgresql://localhost:5432/voluntario");
=======
        dataSource.setUrl("jdbc:postgresql://localhost:5432/voluntariodb");
>>>>>>> Stashed changes
        dataSource.setUsername("postgres");
        dataSource.setPassword("112233");
        dataSource.getConnection().setAutoCommit(false);
        return dataSource;
    }

    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        try {
            return new DataSourceTransactionManager(dataSource());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public Sql2o sql2o() throws SQLException {
        return new Sql2o(dataSource());
    }

}

