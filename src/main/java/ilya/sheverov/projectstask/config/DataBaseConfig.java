package ilya.sheverov.projectstask.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ilya.sheverov.projectstask.entity.converter.TaskTaskPresenterConverter;
import ilya.sheverov.projectstask.entity.validator.TaskObjectValidator;
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

    @Bean
    @Scope("prototype")
    public TaskObjectValidator taskObjectValidator() {
        return new TaskObjectValidator();
    }

    @Bean
    @Scope("prototype")
    public TaskTaskPresenterConverter taskTaskPresenterConverter() {
        return new TaskTaskPresenterConverter(taskObjectValidator());
    }
}
