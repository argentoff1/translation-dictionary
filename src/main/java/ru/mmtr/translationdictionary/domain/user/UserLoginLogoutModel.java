package ru.mmtr.translationdictionary.domain.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mmtr.translationdictionary.domain.common.GeneralResultModel;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: parinos.ma.kst
 * \* Date: 9/12/2023
 * \* Description:
 * \  Модель для методов, которые отдают данные пользователя
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginLogoutModel extends GeneralResultModel {
    @Schema(description = "Идентификатор", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID userId;

    @Schema(description = "Логин", example = "boozy")
    private String login;

    @Schema(description = "Пароль")
    private String password;

    @Schema(description = "Дата создания", example = "2023-08-17 11:55:24.979")
    private LocalDateTime createdAt;

    @Schema(description = "Дата изменения", example = "2023-08-17 23:00:00.000")
    private LocalDateTime modifiedAt;

    @Schema(description = "Дата архивации", example = "2023-08-17 11:55:24.979")
    private LocalDateTime archiveDate;

    @Schema(description = "Роль", example = "Пользователь")
    private String roleName;

    public UserLoginLogoutModel(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}
