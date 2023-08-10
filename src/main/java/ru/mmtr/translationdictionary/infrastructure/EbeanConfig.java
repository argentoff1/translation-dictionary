package ru.mmtr.translationdictionary.infrastructure;

import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mmtr.translationdictionary.domainservice.services.TestConnect;

@Configuration
public class EbeanConfig {
    @Bean
    public Database database(TestConnect currentUser) {
        DatabaseConfig config = new DatabaseConfig();
        config.setCurrentUserProvider(currentUser);
        config.loadFromProperties();
        return DatabaseFactory.create(config);
    }
}
