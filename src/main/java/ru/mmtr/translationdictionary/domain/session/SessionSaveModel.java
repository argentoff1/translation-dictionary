package ru.mmtr.translationdictionary.domain.session;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SessionSaveModel {
    @Parameter(description = "Токен доступа")
    private String accessToken;

    @Parameter(description = "Обновленный токен")
    private String refreshToken;

    @Parameter(description = "Идентификатор пользователя")
    private UUID userId;
}
