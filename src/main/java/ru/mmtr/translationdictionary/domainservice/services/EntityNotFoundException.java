package ru.mmtr.translationdictionary.domainservice.services;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String reason) {
        super(reason);
    }
}
