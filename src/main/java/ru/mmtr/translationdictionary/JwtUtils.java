package ru.mmtr.translationdictionary;

import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.mmtr.translationdictionary.infrastructure.repositories.user.UserRole;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtUtils {
    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setRoleName(claims.get("role", String.class));
        jwtInfoToken.setFirstName(claims.get("firstName", String.class));
        jwtInfoToken.setUserId(claims.get("userId", UUID.class));
        jwtInfoToken.setSessionId(claims.get("sessionId", UUID.class));
        jwtInfoToken.setLogin(claims.getSubject());
        return jwtInfoToken;
    }
}
