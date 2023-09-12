package ru.mmtr.translationdictionary.api.export;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.mmtr.translationdictionary.domain.common.GUIDResultModel;
import ru.mmtr.translationdictionary.domain.export.ExportCreateModel;
import ru.mmtr.translationdictionary.domainservice.export.ExportService;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/export")
@Tag(name = "Экспорт", description = "Контроллер для экспорта данных в Excel")
@SecurityRequirement(name = "JWT")
public class ExportController {
    private final ExportService exportService;

    public ExportController(ExportService exportService) {
        this.exportService = exportService;
    }

    @PostMapping(value = "/createExport")
    @Operation(
            summary = "Экспорт данных в Excel",
            description = "Позволяет экспортировать данные в Excel"
    )
    public GUIDResultModel createExport(@RequestBody ExportCreateModel model) {
        return exportService.createExport(model);
    }

    @GetMapping(value = "/getFile/{id}")
    @Operation(
            summary = "Получение Excel файла с данными",
            description = "Позволяет получить Excel файл, в который были записаны данные"
    )
    public ResponseEntity<InputStreamResource> getFile(@PathVariable @Parameter
            (description = "Идентификатор файла словаря") UUID id) {
        MultipartFile multipartFile = exportService.getFile(id);

        var inputStreamResource = exportService.getInputStreamResource(multipartFile);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + multipartFile.getOriginalFilename())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(multipartFile.getSize())
                .body(inputStreamResource);
    }
}