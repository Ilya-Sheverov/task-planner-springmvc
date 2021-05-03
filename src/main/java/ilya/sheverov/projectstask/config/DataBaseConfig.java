package ilya.sheverov.projectstask.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan("ilya.sheverov.projectstask")
public class DataBaseConfig {

    @Bean
    @Scope("singleton")
    public DataSource hikariDataSource() {
        HikariConfig hikariConfig = new HikariConfig("/hikari-config.properties");
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    @Scope("singleton")
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(hikariDataSource());
    }

}
