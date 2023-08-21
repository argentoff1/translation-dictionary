package ru.mmtr.translationdictionary.infrastructure.repositories.user;

public enum UserRole {
    ROLE_ADMIN("Администратор"),
    ROLE_USER("Пользователь");

    private final String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
