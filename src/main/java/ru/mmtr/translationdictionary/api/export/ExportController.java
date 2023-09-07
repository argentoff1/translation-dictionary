package ru.mmtr.translationdictionary.api.export;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.mmtr.translationdictionary.domain.common.GUIDResultModel;
import ru.mmtr.translationdictionary.domainservice.export.ExportDictionariesService;

import java.util.UUID;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: parinos.ma.kst
 * \* Date: 9/7/2023
 * \* Description:
 * \ Контроллер для экспорта данных в Excel
 */
@RestController
@RequestMapping(value = "/api/export")
@Tag(name = "Экспорт", description = "Контроллер для экспорта данных в Excel")
@SecurityRequirement(name = "JWT")
public class ExportController {
    private final ExportDictionariesService exportDictionariesService;

    public ExportController(ExportDictionariesService exportDictionariesService) {
        this.exportDictionariesService = exportDictionariesService;
    }

    @GetMapping(value = "/exportDictionary")
    @Operation(
            summary = "Экспорт данных в Excel",
            description = "Позволяет экспортировать данные в Excel"
    )
    public GUIDResultModel exportDictionary() {
        return exportDictionariesService.exportDictionary();
    }

    @GetMapping(value = "/getExportDictionary/{id}")
    @Operation(
            summary = "Получение Excel файла с данными",
            description = "Позволяет получить Excel файл, в который были записаны данные"
    )
    public ResponseEntity<MultipartFile> getExportDictionary(@PathVariable @Parameter
            (description = "Идентификатор файла словаря") UUID id) {
        MultipartFile exportDictionary = exportDictionariesService.getExportDictionary(id);

        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "ffffff");
        headers.add(HttpHeaders.CONTENT_TYPE, "multipart/form-data");

        return new ResponseEntity(exportDictionary.getBytes(), headers, HttpStatus.OK);
    }
}
