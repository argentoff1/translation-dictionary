package ru.mmtr.translationdictionary.domainservice.common;

import org.springframework.security.core.context.SecurityContextHolder;
import ru.mmtr.translationdictionary.infrastructure.security.JwtAuthentication;

import java.util.UUID;

public class CommonUtils {

    public static JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

    public static String getRole() {
        var user = new JwtAuthentication();

        try {
            user = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        } catch (NullPointerException e) {
            e.getMessage();
        }

        return user.getRoleName();
    }

    public static UUID getUserId() {
        var user = new JwtAuthentication();

        try {
            user = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        } catch (NullPointerException e) {
            e.getMessage();
        }

        return user.getUserId();
    }

    public static UUID getSessionId() {
        var user = new JwtAuthentication();

        try {
            user = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        } catch (NullPointerException e) {
            e.getMessage();
        }

        return user.getSessionId();
    }

    public static String getSubject() {
        var user = new JwtAuthentication();

        try {
            user = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (NullPointerException e) {
            e.getMessage();
        }

        return user.getLogin();
    }

    public static String getRefreshToken() {
        var user = new JwtAuthentication();

        try {
            user = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (NullPointerException e) {
            e.getMessage();
        }

        return user.getLogin();
    }
}
