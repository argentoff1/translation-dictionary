package ru.mmtr.translationdictionary.domain.export;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;
import ru.mmtr.translationdictionary.domainservice.export.ExportModelsEnum;

@Getter
@Setter
public class CreateExportModel {
    @Parameter(description = "Сущность для экспорта")
    private ExportModelsEnum model;
}
