package ru.mmtr.translationdictionary.domainservice.common;

import org.springframework.security.core.context.SecurityContextHolder;
import ru.mmtr.translationdictionary.JwtAuthentication;

import java.util.UUID;

public class CommonUtils {
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
}
