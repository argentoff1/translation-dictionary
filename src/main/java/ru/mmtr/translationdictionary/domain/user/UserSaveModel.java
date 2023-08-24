package ru.mmtr.translationdictionary.domain.user;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSaveModel {
    @Parameter(description = "Логин", example = "alexx")
    private String login;

    @Parameter(description = "Пароль")
    private String password;

    @Parameter(description = "Фамилия", example = "Alexandrov")
    private String lastName;

    @Parameter(description = "Имя", example = "Alexander")
    private String firstName;

    @Parameter(description = "Отчество", example = "Alexandrovich")
    private String fatherName;

    @Parameter(description = "Электронная почта", example = "alexx@gmail.com")
    private String email;

    @Parameter(description = "Номер телефона", example = "88005553535")
    private String phoneNumber;
}