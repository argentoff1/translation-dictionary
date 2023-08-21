package ru.mmtr.translationdictionary.domain.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    @Schema(description = "Идентификатор", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID userId;

    @Schema(description = "Логин", example = "repeatalexander")
    private String login;

    @Schema(description = "Пароль", accessMode = Schema.AccessMode.READ_ONLY)
    private String password;

    @Schema(description = "Фамилия", example = "Александров")
    private String lastName;

    @Schema(description = "Имя", example = "Александр")
    private String firstName;

    @Schema(description = "Отчество", example = "Александрович")
    private String fatherName;

    @Schema(description = "Электронная почта", example = "alexandrov.alexander@mail.ru")
    private String email;

    @Schema(description = "Номер телефона", example = "88005553535", accessMode = Schema.AccessMode.READ_ONLY)
    private String phoneNumber;

    @Schema(description = "Дата создания", example = "2023-08-17 11:55:24.979")
    private LocalDateTime createdAt;

    @Schema(description = "Дата изменения", example = "2023-08-17 23:00:00.000")
    private LocalDateTime modifiedAt;

    @Schema(description = "Дата архивации", example = "2023-08-17 11:55:24.979")
    private LocalDateTime archiveDate;
}
