package ru.mmtr.translationdictionary;

import io.jsonwebtoken.*;
import io.micrometer.common.lang.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
public class JwtGeneration {
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

    public boolean validateAccessToken(@NonNull String accessToken) {
        return validateToken(accessToken, secretAccess);
    }

    public boolean validateRefreshToken(@NonNull String refreshToken) {
        return validateToken(refreshToken, secretRefresh);
    }

    private boolean validateToken(@NonNull String token, @NonNull String secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretAccess)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
        } catch (SignatureException sEx) {
            log.error("Invalid signature", sEx);
        } catch (Exception e) {
            log.error("invalid token", e);
        }
        return false;
    }

    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, secretAccess);
    }

    public Claims getRefreshClaims(@NonNull String token) {
        return getClaims(token, secretRefresh);
    }

    private Claims getClaims(@NonNull String token, @NonNull String secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
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
        return Jwts.parser().setSigningKey(secretAccess).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        Date expirationDate = extractExpirationDate(token);
        return expirationDate.before(new Date());
    }*/
}
