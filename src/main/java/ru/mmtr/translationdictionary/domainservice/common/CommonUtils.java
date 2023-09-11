package ru.mmtr.translationdictionary.domainservice.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.mmtr.translationdictionary.infrastructure.security.JwtAuthentication;

import java.util.UUID;

@Slf4j
public class CommonUtils {
    public static JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) getAuthentication();
    }

    public static String getRole() {
        var user = new JwtAuthentication();

        try {
            user = (JwtAuthentication) getAuthentication().getCredentials();
        } catch (NullPointerException e) {
            log.error(e.getMessage(), e);
        }

        return user.getRoleName();
    }

    public static UUID getUserId() {
        var user = new JwtAuthentication();

        try {
            user = (JwtAuthentication) getAuthentication().getCredentials();
        } catch (NullPointerException e) {
            log.error(e.getMessage(), e);
        }

        return user.getUserId();
    }

    public static UUID getSessionId() {
        var user = new JwtAuthentication();

        try {
            user = (JwtAuthentication) getAuthentication().getCredentials();
        } catch (NullPointerException e) {
            log.error(e.getMessage(), e);
        }

        return user.getSessionId();
    }

    public static String getSubject() {
        var user = new JwtAuthentication();

        try {
            user = (JwtAuthentication) getAuthentication();

            Object principal = user.getPrincipal();

            if (principal instanceof String) {
                return user.getLogin();
            }
        } catch (NullPointerException e) {
            log.error(e.getMessage(), e);
        }

        return user.getLogin();
    }

    public static String getRefreshToken() {
        var user = new JwtAuthentication();

        try {
            user = (JwtAuthentication) getAuthentication().getPrincipal();
        } catch (NullPointerException e) {
            log.error(e.getMessage(), e);
        }

        return user.getLogin();
    }

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
