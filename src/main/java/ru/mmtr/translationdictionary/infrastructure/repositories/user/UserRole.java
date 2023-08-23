package ru.mmtr.translationdictionary.infrastructure.repositories.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Getter
@RequiredArgsConstructor
public enum UserRole implements GrantedAuthority {
    ADMIN("ADMIN"),
    USER("USER");

    private final String roleName;

    @Override
    public String getAuthority() {
        return roleName;
    }
}
