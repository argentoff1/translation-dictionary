package ru.mmtr.translationdictionary;

import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfiguration {
    @Value("${postgres.sql.db-username}")
    private String username;

    @Value("${postgres.sql.db-password}")
    private String password;

    @Value("${postgres.sql.db-url}")
    private String databaseUrl;

    @Value("${postgres.sql.driver}")
    private String driverClassName;

    public DataSourceConfig getDataSourceConfig() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDriver(driverClassName);
        dataSourceConfig.setUsername(username);
        dataSourceConfig.setPassword(password);
        dataSourceConfig.setUrl(databaseUrl);

        return dataSourceConfig;
    }

    @Bean
    public Database createDatabase() {
        DatabaseConfig databaseConfig = new DatabaseConfig();
        databaseConfig.setDataSourceConfig(getDataSourceConfig());
        return DatabaseFactory.create(databaseConfig);
    }
}
