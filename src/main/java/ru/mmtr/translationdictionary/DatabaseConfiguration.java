package ru.mmtr.translationdictionary;

import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {
    /*@Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.url}")
    private String databaseUrl;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;*/

    public DataSourceConfig getDataSourceConfig() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDriver("org.postgresql.Driver");
        dataSourceConfig.setUsername("postgres");
        dataSourceConfig.setPassword("postgres");
        dataSourceConfig.setSchema("public");
        dataSourceConfig.setUrl("jdbc:postgresql://localhost:5432/translation_dictionaries");

        return dataSourceConfig;
    }

    @Bean
    public Database createDatabase(){
        DatabaseConfig databaseConfig = new DatabaseConfig();
        databaseConfig.setDataSourceConfig(getDataSourceConfig());
        return DatabaseFactory.create(databaseConfig);
    }
}
