package ru.mmtr.translationdictionary;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

@Component
@Slf4j
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
    @Value("${liquibase.change-log}")
    private String liquibaseChangeLog;

    @Value("${liquibase.schema-name}")
    private String liquibaseSchemaName;

    private final DatabaseConfiguration serverConfig;

    @Autowired
    public ApplicationStartup(DatabaseConfiguration serverConfig) {
        this.serverConfig = serverConfig;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        applyDbMigrations(serverConfig.createDatabase().dataSource());
    }

    public void applyDbMigrations(DataSource server) {
        ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor(getClass().getClassLoader());

        log.info("Liquibase changes applying started");

        try (var connection = server.getConnection()) {
            try {
                DatabaseConnection databaseConnection = new JdbcConnection(connection);
                var database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(databaseConnection);

                database.setLiquibaseSchemaName(liquibaseSchemaName);
                database.setDefaultSchemaName(liquibaseSchemaName);

                Liquibase liquibase = new Liquibase(liquibaseChangeLog, resourceAccessor, database);
                liquibase.update(new Contexts(), new LabelExpression());

                log.info("Liquibase changes applying completed successfully");
            } catch (LiquibaseException ex) {
                log.error(ex.getMessage(), ex);

                log.info("Liquibase changes applying failed");
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);

            log.info("Liquibase changes applying failed");
        }
    }
}
