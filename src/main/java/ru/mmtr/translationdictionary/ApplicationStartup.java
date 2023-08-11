package ru.mmtr.translationdictionary;

import io.ebean.datasource.DataSourceConfig;
import io.ebeaninternal.server.core.DefaultServer;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.DatabaseConnection;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

    }

    public void applyDbMigrations(DefaultServer server, String liquibaseSchemaName, String springLiquibaseChangeLog) {
        ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor(getClass().getClassLoader());

        System.out.println("------------Liquibase changes applying started---------");

        var dataSource = server.dataSource();

        try (var connection = dataSource.getConnection()) {
            try {
                DatabaseConnection databaseConnection = new JdbcConnection(connection);
                var database = liquibase.database.DatabaseFactory.getInstance().findCorrectDatabaseImplementation(databaseConnection);

                database.setLiquibaseSchemaName(liquibaseSchemaName);
                database.setDefaultSchemaName(liquibaseSchemaName);

                Liquibase liquibase = new Liquibase(springLiquibaseChangeLog, resourceAccessor, database);
                liquibase.update(new Contexts(), new LabelExpression());

                System.out.println("------------Liquibase changes applying completed successfully---------");
            } catch (LiquibaseException ex) {
                System.out.println(ex.getMessage());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
