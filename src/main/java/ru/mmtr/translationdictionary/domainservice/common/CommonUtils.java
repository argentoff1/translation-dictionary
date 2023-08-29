package ru.mmtr.translationdictionary.domainservice.common;

import org.springframework.security.core.context.SecurityContextHolder;
import ru.mmtr.translationdictionary.JwtAuthentication;

import java.util.UUID;

public class CommonUtils {
    public static String getRole() {
        var user = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        return user.getRoleName();
    }

    public static UUID getUserId() {
        var user = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        // Проверки на null. try/catch

        return user.getUserId();
    }

    public static UUID getSessionId() {
        var user = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        return user.getSessionId();
    }

    public static String getSubject() {
        var user = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return user.getLogin();
    }
}
