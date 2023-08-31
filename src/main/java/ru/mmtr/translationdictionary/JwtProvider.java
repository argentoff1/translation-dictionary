package ru.mmtr.translationdictionary;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.mmtr.translationdictionary.domain.user.UserModel;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Component
@Slf4j
public class JwtProvider {
    private final SecretKey jwtAccessSecret;
    private final SecretKey jwtRefreshSecret;

    public JwtProvider(
            @Value("${jwt.secret.access}") String jwtAccessSecret,
            @Value("${jwt.secret.refresh}") String jwtRefreshSecret
    ) {
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
        this.jwtRefreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));
    }

    public String generateAccessToken(@NonNull UserModel user, UUID sessionId) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusMinutes(10).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);

        return Jwts.builder()
                .setSubject(user.getLogin())
                .setIssuer("dictionaries-api")
                .setAudience("translation-dictionary-app")
                .setExpiration(accessExpiration)
                .signWith(jwtAccessSecret)
                .claim("role", user.getRoleName())
                .claim("userId", user.getUserId())
                .claim("sessionId", sessionId)
                .compact();
    }

    public String generateRefreshToken(@NonNull UserModel user, UUID sessionId) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant refreshExpirationInstant = now.plusDays(30).atZone(ZoneId.systemDefault()).toInstant();
        final Date refreshExpiration = Date.from(refreshExpirationInstant);

        return Jwts.builder()
                .setSubject(user.getLogin())
                .setIssuer("dictionaries-api")
                .setAudience("translation-dictionary-app")
                .setExpiration(refreshExpiration)
                .signWith(jwtRefreshSecret)
                .claim("role", user.getRoleName())
                .claim("userId", user.getUserId())
                .claim("sessionId", sessionId)
                .compact();
    }

    public boolean validateAccessToken(@NonNull String accessToken) {
        return validateToken(accessToken, jwtAccessSecret);
    }

    public boolean validateRefreshToken(@NonNull String refreshToken) {
        return validateToken(refreshToken, jwtRefreshSecret);
    }

    private boolean validateToken(@NonNull String token, @NonNull Key secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
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
        return getClaims(token, jwtAccessSecret);
    }

    public Claims getRefreshClaims(@NonNull String token) {
        return getClaims(token, jwtRefreshSecret);
    }

    private Claims getClaims(@NonNull String token, @NonNull Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }















    /*@Value("${jwt.secret.access}")
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
    }*/









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
