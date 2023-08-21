package ru.mmtr.translationdictionary.domain.user;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSaveModel {
    @Parameter(description = "Логин")
    private String login;

    @Parameter(description = "Пароль")
    private String password;

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
