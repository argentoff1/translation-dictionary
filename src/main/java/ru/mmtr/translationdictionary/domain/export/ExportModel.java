package ru.mmtr.translationdictionary.domain.export;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mmtr.translationdictionary.domain.common.GeneralResultModel;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExportModel extends GeneralResultModel {
    @Schema(description = "Идентификатор")
    private UUID exportId;

    @Schema(description = "Тип сущности для экспорта")
    private String type;

    @Schema(description = "JSON объект создания экспорта")
    private JsonNode createModel;

    @Schema(description = "Идентификатор создателя экспорта")
    private UUID createdUserId;

    @Schema(description = "Дата создания")
    private LocalDateTime createdAt;

    public ExportModel(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}
