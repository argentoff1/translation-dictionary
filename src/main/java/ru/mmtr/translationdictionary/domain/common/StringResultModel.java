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
public class StringResultModel extends GeneralResultModel {
    @Schema(description = "Результирующая строка")
    private String stringResult;

    public StringResultModel(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
        stringResult = null;
    }
}
