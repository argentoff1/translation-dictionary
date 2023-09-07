package ru.mmtr.translationdictionary.api.export;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.mmtr.translationdictionary.domain.common.GUIDResultModel;
import ru.mmtr.translationdictionary.domainservice.export.ExportDictionariesService;

import java.io.IOException;
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

    @GetMapping(value = "/getExportDictionary/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Operation(
            summary = "Получение Excel файла с данными",
            description = "Позволяет получить Excel файл, в который были записаны данные"
    )
    public ResponseEntity<InputStreamResource> getExportDictionary(@PathVariable @Parameter
            (description = "Идентификатор файла словаря") UUID id) {
        MultipartFile multipartFile = exportDictionariesService.getExportDictionary(id);

        InputStreamResource inputStreamResource;
        try {
            inputStreamResource = new InputStreamResource(multipartFile.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + multipartFile.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(multipartFile.getSize())
                .body(inputStreamResource);
    }
}
