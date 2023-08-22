package ru.mmtr.translationdictionary.domain.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenResultModel extends GeneralResultModel {
    @Schema(description = "Токен доступа")
    private Integer accessToken;

    @Schema(description = "Обновленный токен доступа")
    private Integer refreshToken;

    public TokenResultModel(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
        String accessToken = null;
        String refreshToken = null;
    }

    public TokenResultModel(Integer accessToken, Integer refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
