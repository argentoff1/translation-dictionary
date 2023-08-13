package ru.mmtr.translationdictionary;

import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;
import io.ebean.event.ServerConfigStartup;
import io.ebeaninternal.server.core.DefaultServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
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
    @Bean
    public DataSourceConfig getDataSourceBuilder() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDriver("org.postgresql.Driver");
        dataSourceConfig.setUsername("postgres");
        dataSourceConfig.setPassword("postgres");
        dataSourceConfig.setUrl("jdbc:postgresql://localhost:5432/translation_dictionaries");
        dataSourceBuilder.driverClassName("org.h2.Driver");
        /*dataSourceBuilder.url("jdbc:h2:mem:translation_dictionaries");
        dataSourceBuilder.username("postgres");
        dataSourceBuilder.password("postgres");
        dataSourceBuilder.build();*/
        return dataSourceConfig;



    }

    /*@Bean
    public static DataSourceConfig getDataSourceConfig() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDriver("org.postgresql.Driver");
        dataSourceConfig.setUsername("postgres");
        dataSourceConfig.setPassword("postgres");
        dataSourceConfig.setUrl("jdbc:postgresql://localhost:5432/translation_dictionaries");
        return dataSourceConfig;
    }*/

    @Bean
    public Database createDatabase(){
        DatabaseConfig databaseConfig = new DatabaseConfig();
        databaseConfig.setDataSourceConfig(getDataSourceBuilder());
        return DatabaseFactory.create(databaseConfig);
    }

    /*@Bean
    public ServerConfigStartup getDefaultServer() {
        ServerConfigStartup serverConfigStartup = new ServerConfigStartup() {
            @Override
            public void onStart(DatabaseConfig databaseConfig) {
                databaseConfig.setDataSourceConfig(getDataSourceBuilder());
            }
        };
        return serverConfigStartup;
    }*/
}
