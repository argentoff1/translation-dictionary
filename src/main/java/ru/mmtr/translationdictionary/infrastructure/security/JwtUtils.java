package ru.mmtr.translationdictionary.infrastructure.security;

import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtUtils {
    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setRoleName(claims.get("role", String.class));
        jwtInfoToken.setUserId(UUID.fromString(claims.get("userId", String.class)));
        jwtInfoToken.setSessionId(UUID.fromString(claims.get("sessionId", String.class)));
        jwtInfoToken.setLogin(claims.getSubject());
        return jwtInfoToken;
    }
}
