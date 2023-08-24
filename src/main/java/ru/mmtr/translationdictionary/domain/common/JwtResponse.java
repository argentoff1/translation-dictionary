package ru.mmtr.translationdictionary.domain.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JwtResponse extends GeneralResultModel{
    @Schema(description = "Токен доступа")
    private String accessToken;

    @Schema(description = "Обновленный токен доступа")
    private String refreshToken;

    private final String type = "Bearer";

    public JwtResponse(String errorCode) {
        super(errorCode);
        accessToken = null;
        refreshToken = null;
    }

    public JwtResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
