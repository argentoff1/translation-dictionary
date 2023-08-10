package ru.mmtr.translationdictionary.domainservice.services;

import io.ebean.config.CurrentUserProvider;
import org.springframework.stereotype.Component;

@Component
public class TestConnect implements CurrentUserProvider {
    @Override
    public Object currentUser() {
        return "postgres";
    }
}
