package ru.mmtr.translationdictionary;

import io.ebean.datasource.DataSourceConfig;
import io.ebean.event.ServerConfigStartup;
import io.ebeaninternal.server.core.DefaultServer;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
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
    private DatabaseConfiguration serverConfig;

    @Autowired
    public ApplicationStartup(DatabaseConfiguration serverConfig) {
        this.serverConfig = serverConfig;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        applyDbMigrations(serverConfig.createDatabase().dataSource(), "public", "db/changelog/09082023-db.changelog-master.xml");
    }

    public void applyDbMigrations(DataSource server, String liquibaseSchemaName, String springLiquibaseChangeLog) {
        /*ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor(getClass().getClassLoader());

        log.info("------------Liquibase changes applying started---------");

        var dataSource = server.dataSource();

        try (var connection = dataSource.getConnection()) {
            try {
                DatabaseConnection databaseConnection = new JdbcConnection(connection);
                var database = liquibase.database.DatabaseFactory.getInstance().findCorrectDatabaseImplementation(databaseConnection);

                database.setLiquibaseSchemaName(liquibaseSchemaName);
                database.setDefaultSchemaName(liquibaseSchemaName);

                Liquibase liquibase = new Liquibase(springLiquibaseChangeLog, resourceAccessor, database);
                liquibase.update(new Contexts(), new LabelExpression());

                log.info("------------Liquibase changes applying completed successfully---------");
            } catch (LiquibaseException ex) {
                log.error(ex.getMessage(), ex);

                log.info("---------Liquibase changes applying failed");
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);

            log.info("---------Liquibase changes applying failed");
        }*/


        ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor(getClass().getClassLoader());

        log.info("------------Liquibase changes applying started---------");

        try (var connection = server.getConnection()) {
            try {
                DatabaseConnection databaseConnection = new JdbcConnection(connection);
                var database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(databaseConnection);

                database.setLiquibaseSchemaName(liquibaseSchemaName);
                database.setDefaultSchemaName(liquibaseSchemaName);

                Liquibase liquibase = new Liquibase(springLiquibaseChangeLog, resourceAccessor, database);
                liquibase.update(new Contexts(), new LabelExpression());

                log.info("------------Liquibase changes applying completed successfully---------");
            } catch (LiquibaseException ex) {
                log.error(ex.getMessage(), ex);

                log.info("---------Liquibase changes applying failed");
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);

            log.info("---------Liquibase changes applying failed");
        }

    }
}
