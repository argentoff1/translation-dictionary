package ru.mmtr.translationdictionary.domain.user;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRequestModel {
    @Parameter(description = "Логин", example = "alex")
    private String login;

    @Parameter(description = "Пароль")
    private String password;
}
