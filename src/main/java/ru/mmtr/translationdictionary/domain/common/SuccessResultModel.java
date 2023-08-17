package ru.mmtr.translationdictionary.domain.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@NoArgsConstructor
public class SuccessResultModel extends GeneralResultModel {
    @Schema(description = "Успешно", example = "true")
    private Boolean success;

    //private var entity;

    public SuccessResultModel(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
        success = false;
    }

    public SuccessResultModel(boolean success) {
        this.success = success;
    }
}
