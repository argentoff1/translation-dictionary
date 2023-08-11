package ru.mmtr.translationdictionary;

import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {
    @Value("${datasource.db.username}")
    private String username;

    @Value("${datasource.db.password}")
    private String password;

    @Value("${datasource.db.databaseUrl}")
    private String databaseUrl;

    @Value("${datasource.db.databaseDriver}")
    private String driverClassName;
    @Bean
    public DataSource createConnection() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driverClassName);
        dataSourceBuilder.url(databaseUrl);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }
}
