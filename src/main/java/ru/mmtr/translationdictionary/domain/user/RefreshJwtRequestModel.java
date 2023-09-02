package ru.mmtr.translationdictionary.domain.user;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RefreshJwtRequestModel {
    @Parameter(description = "Обновленный токен доступа")
    private String refreshToken;
}
