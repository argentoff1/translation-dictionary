package ru.mmtr.translationdictionary;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtConfiguration {
    @Value("${jwt.secret.access}")
    private String secretAccess;

    @Value("${jwt.secret.refresh}")
    private String secretRefresh;

    @Value("${jwt.expiration.access}")
    private Long expirationAccess;

    @Value("${jwt.expiration.refresh}")
    private Long expirationRefresh;

    public String generateAccessToken(String role, UUID userId, UUID sessionId) {
        Map<String, Object> claims = new HashMap<>();
        return createAccessToken(claims, role, userId, sessionId);
    }

    private String createAccessToken(Map<String, Object> claims, String role, UUID userId, UUID sessionId) {
        claims.put("role", role);
        claims.put("userId", userId);
        claims.put("sessionId", sessionId);

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expirationAccess);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("dictionaries-api")
                .setAudience("dictionaries-app")
                .setSubject("parinos.ma@mmtr.ru")
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secretAccess)
                .compact();
    }

    public String generateRefreshToken(String role, UUID userId, UUID sessionId) {
        Map<String, Object> claims = new HashMap<>();
        return createRefreshToken(claims, role, userId, sessionId);
    }

    private String createRefreshToken(Map<String, Object> claims, String role, UUID userId, UUID sessionId) {
        claims.put("role", role);
        claims.put("userId", userId);
        claims.put("sessionId", sessionId);

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expirationRefresh);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("dictionaries-api")
                .setAudience("dictionaries-app")
                .setSubject("parinos.ma@mmtr.ru")
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secretRefresh)
                .compact();
    }

    /*public Boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        Date expirationDate = extractExpirationDate(token);
        return expirationDate.before(new Date());
    }*/
}
