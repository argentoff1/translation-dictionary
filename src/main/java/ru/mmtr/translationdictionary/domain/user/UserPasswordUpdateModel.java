package ru.mmtr.translationdictionary.domain.user;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserPasswordUpdateModel {
    @Parameter(description = "Идентификатор")
    private UUID id;

    // Пока что без хэширования
    @Parameter(description = "Пароль")
    private String password;
}
