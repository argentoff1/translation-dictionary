package ru.mmtr.translationdictionary.infrastructure.repositories.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    ADMIN("ADMIN"),
    USER("USER");

    private final String roleName;
}
