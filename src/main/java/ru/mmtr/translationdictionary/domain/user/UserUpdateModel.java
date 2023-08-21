package ru.mmtr.translationdictionary.domain.user;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserUpdateModel {
    @Parameter(description = "Идентификатор")
    private UUID id;

    @Parameter(description = "Логин")
    private String login;

    @Parameter(description = "Фамилия")
    private String lastName;

    @Parameter(description = "Имя")
    private String firstName;

    @Parameter(description = "Отчество")
    private String fatherName;

    @Parameter(description = "Электронная почта")
    private String email;

    @Parameter(description = "Номер телефона")
    private String phoneNumber;
}
