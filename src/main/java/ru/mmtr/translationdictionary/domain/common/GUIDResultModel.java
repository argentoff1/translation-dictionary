package ru.mmtr.translationdictionary.domain.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GUIDResultModel extends GeneralResultModel {
    @Schema(description = "Идентификатор")
    private UUID resultId;

    public GUIDResultModel(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
        resultId = null;
    }
}