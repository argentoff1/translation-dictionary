package ru.mmtr.translationdictionary;

import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.mmtr.translationdictionary.infrastructure.repositories.user.UserRole;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtUtils {
    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setRoleName(UserRole.ADMIN.getRoleName());
        jwtInfoToken.setFirstName(claims.get("firstName", String.class));
        jwtInfoToken.setLogin(claims.getSubject());
        return jwtInfoToken;
    }
}
