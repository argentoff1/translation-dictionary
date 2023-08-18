package ru.mmtr.translationdictionary.domain.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DateTimeResultModel extends GeneralResultModel {
    @Schema(description = "Дата создания")
    private LocalDateTime createdAt;

    @Schema(description = "Дата изменения")
    private LocalDateTime modifiedAt;

    public DateTimeResultModel(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
        createdAt = null;
        modifiedAt = null;
    }

    public DateTimeResultModel(LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
