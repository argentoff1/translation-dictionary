package ru.mmtr.translationdictionary.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Getter
@RequiredArgsConstructor
public enum UserRole implements GrantedAuthority {
    ADMIN("ADMIN"),
    USER("USER"),
    REFRESH_TOKEN("REFRESH_TOKEN");

    private final String roleName;

    @Override
    public String getAuthority() {
        return roleName;
    }
}
