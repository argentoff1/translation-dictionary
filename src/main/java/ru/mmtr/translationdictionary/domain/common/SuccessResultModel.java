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
public class SuccessResultModel extends GeneralResultModel {
    @Schema(description = "Успешно", example = "true")
    private Boolean success;

    public SuccessResultModel(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
        success = false;
    }
}
