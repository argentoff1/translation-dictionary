package ru.mmtr.translationdictionary.infrastructure.repositories.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    ADMIN("ADMIN"),
    USER("USER"),
    REFRESH_TOKEN("REFRESH_TOKEN");

    private final String roleName;
}
