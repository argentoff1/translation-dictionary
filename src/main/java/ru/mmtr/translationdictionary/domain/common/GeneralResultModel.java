package ru.mmtr.translationdictionary.domain.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeneralResultModel {
    @Schema(description = "Код ошибки", example = "LANGUAGE_NOT_FOUND")
    private String errorCode;

    @Schema(description = "Сообщение ошибки", example = "Не удалось найти язык")
    private String errorMessage;

    public boolean isError() {
        return StringUtils.isNotBlank(errorCode) || StringUtils.isNotBlank(errorMessage);
    }
}
