package ru.mmtr.translationdictionary.domain.export;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExportCreateModel {
    @Parameter(description = "Тип сущности для экспорта")
    private ExportType exportType;

    // Фильтры по дате и т.д.
}
