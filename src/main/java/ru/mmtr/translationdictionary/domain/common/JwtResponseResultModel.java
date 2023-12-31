package ru.mmtr.translationdictionary.domain.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponseResultModel extends GeneralResultModel {
    @Schema(description = "Тип токена")
    private final String type = "Bearer";

    @Schema(description = "Токен доступа")
    private String accessToken;

    @Schema(description = "Обновленный токен доступа")
    private String refreshToken;

    public JwtResponseResultModel(String errorCode) {
        super(errorCode);
        accessToken = null;
        refreshToken = null;
    }
}
