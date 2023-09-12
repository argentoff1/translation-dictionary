package ru.mmtr.translationdictionary.domain.export;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: parinos.ma.kst
 * \* Date: 9/11/2023
 * \* Description:
 * \  Модель для создания экспорта
 */
@Getter
@Setter
public class ExportCreateModel {
    @Parameter(description = "Тип сущности для экспорта")
    private ExportType exportType;
}
