package ru.mmtr.translationdictionary.domain.export;

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
public class ExportUserModel {
    @Schema(description = "Идентификатор пользователя")
    private UUID id;

    @Schema(description = "Логин")
    private String login;

    @Schema(description = "Фамилия")
    private String lastName;

    @Schema(description = "Имя")
    private String firstName;

    @Schema(description = "Отчество")
    private String fatherName;

    @Schema(description = "ФИО")
    private String fullName;

    @Schema(description = "Электронная почта")
    private String email;

    @Schema(description = "Номер телефона")
    private String phoneNumber;

    @Schema(description = "Дата добавления пары слов")
    private LocalDateTime createdAt;

    @Schema(description = "Дата изменения пары слов")
    private LocalDateTime modifiedAt;

    @Schema(description = "Дата архивации")
    private LocalDateTime archiveDate;

    @Schema(description = "Наименование роли")
    private String roleName;
}
