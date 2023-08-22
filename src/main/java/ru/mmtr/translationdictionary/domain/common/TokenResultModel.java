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
    private String accessToken;

    @Schema(description = "Обновленный токен доступа")
    private String refreshToken;

    public TokenResultModel(String errorCode, String errorMessage, String accessToken, String refreshToken) {
        super(errorCode, errorMessage);
        accessToken = null;
        refreshToken = null;
    }



    public TokenResultModel(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
