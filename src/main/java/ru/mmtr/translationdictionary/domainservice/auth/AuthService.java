package ru.mmtr.translationdictionary.domainservice.auth;

import io.jsonwebtoken.Claims;
import io.micrometer.common.lang.NonNull;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mmtr.translationdictionary.JwtProvider;
import ru.mmtr.translationdictionary.domain.common.JwtResponse;
import ru.mmtr.translationdictionary.domain.user.JwtRequest;
import ru.mmtr.translationdictionary.domain.user.UserModel;
import ru.mmtr.translationdictionary.domainservice.user.UserService;

import java.util.HashMap;
import java.util.Map;

/*@Service
@RequiredArgsConstructor*/
public class AuthService {
    /*private final UserService userService;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtProvider jwtProvider;

    public JwtResponse login(@NonNull JwtRequest authRequest) throws AuthException {
        final UserModel user = userService.getByLogin(authRequest.getLogin());
        if (user.getPassword().equals(authRequest.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            refreshStorage.put(user.getLogin(), refreshToken);
            return new JwtResponse(accessToken, refreshToken);
        } else {
            return new JwtResponse("PASSWORD_NOT_FOUND");
        }
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final UserModel user = userService.getByLogin(login);
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse refresh(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final UserModel user = userService.getByLogin(login);
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                refreshStorage.put(user.getLogin(), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Невалидный JWT токен");
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }*/
}
