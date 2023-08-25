package ru.mmtr.translationdictionary.domain.user;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class JwtRequestModel {
    /*@Parameter(description = "Идентификатор пользователя")
    private UUID userId;

    @Parameter(description = "Идентификатор сессии")
    private UUID sessionId;*/

    @Parameter(description = "Логин", example = "alexx")
    private String login;

    @Parameter(description = "Пароль")
    private String password;
}