package ru.mmtr.translationdictionary.domain.session;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mmtr.translationdictionary.domain.common.GeneralResultModel;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSessionModel extends GeneralResultModel {
    @Schema(description = "Идентификатор")
    private UUID sessionId;

    @Schema(description = "Токен доступа")
    private String accessToken;

    @Schema(description = "Обновленный токен")
    private String refreshToken;

    @Schema(description = "Дата создания токена")
    private LocalDateTime tokenCreatedAt;

    @Schema(description = "Дата истечения токена")
    private LocalDateTime accessTokenExpiredDate;

    @Schema(description = "Дата истечения обновленного токена")
    private LocalDateTime refreshTokenExpiredDate;

    @Schema(description = "Идентификатор пользователя")
    private UUID userId;

    public UserSessionModel(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
        sessionId = null;
        accessToken = null;
        refreshToken = null;
        tokenCreatedAt = null;
        accessTokenExpiredDate = null;
        refreshTokenExpiredDate = null;
        userId = null;
    }
}
