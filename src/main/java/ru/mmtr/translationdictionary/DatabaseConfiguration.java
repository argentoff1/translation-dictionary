package ru.mmtr.translationdictionary;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.EbeanServer;
import io.ebean.EbeanServerFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.config.ServerConfig;
import io.ebean.datasource.DataSourceConfig;
import io.ebean.event.ServerConfigStartup;
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

    public HikariDataSource getDataSourceConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setJdbcUrl(databaseUrl);
        hikariConfig.setJdbcUrl(databaseUrl);

        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public DatabaseModel createDatabase() {
        DatabaseModel databaseModel = new DatabaseModel();
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setDataSource(getDataSourceConfig());
        serverConfig.setAllQuotedIdentifiers(true);
        serverConfig.setExpressionNativeIlike(true);
        serverConfig.addPackage("");
        serverConfig.setName("server");
        serverConfig.setDefaultServer(true);
        serverConfig.setRegister(true);

        EbeanServer server = EbeanServerFactory.create(serverConfig);
        databaseModel.setDatabase(server);

        return databaseModel;
        /*databaseConfig.setDataSourceConfig(getDataSourceConfig());
        return DatabaseFactory.create(databaseConfig);*/
    }
}
