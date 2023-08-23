package ru.mmtr.translationdictionary.infrastructure.repositories.user;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("Администратор"),
    USER("Пользователь");

    private final String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }
}
