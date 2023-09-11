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
import ru.mmtr.translationdictionary.domainservice.export.ExportDictionaryStrategy;
import ru.mmtr.translationdictionary.domainservice.export.ExportLanguageStrategy;
import ru.mmtr.translationdictionary.domainservice.export.oldversion.ExportDictionariesService;
import ru.mmtr.translationdictionary.domainservice.export.oldversion.ExportLanguagesService;
import ru.mmtr.translationdictionary.domainservice.export.ExportType;
import ru.mmtr.translationdictionary.domainservice.export.FileStorageService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/export")
@Tag(name = "Экспорт", description = "Контроллер для экспорта данных в Excel")
@SecurityRequirement(name = "JWT")
public class ExportController {
    private FileStorageService fileStorageService;
    private final ExportDictionariesService exportDictionariesService;
    private final ExportLanguagesService exportLanguagesService;

    public ExportController(FileStorageService fileStorageService, ExportDictionariesService exportDictionariesService, ExportLanguagesService exportLanguagesService) {
        this.fileStorageService = fileStorageService;
        this.exportDictionariesService = exportDictionariesService;
        this.exportLanguagesService = exportLanguagesService;
    }

    @PostMapping(value = "/createExport")
    @Operation(
            summary = "Экспорт данных в Excel",
            description = "Позволяет экспортировать данные в Excel"
    )
    // RequestBody - "ExportType: 0 || 1"
    public GUIDResultModel createExport(@RequestBody ExportType exportType) {
        switch (exportType) {
            case LANGUAGE -> fileStorageService = new ExportLanguageStrategy();
            case DICTIONARY -> fileStorageService = new ExportDictionaryStrategy();
            default -> {
                return new GUIDResultModel("CAN_NOT_EXPORT",
                        "Введенный объект невозможно экспортировать");
            }
        }

        return fileStorageService.createExport();
    }

    // +
    @GetMapping(value = "/getFile/{id}")
    @Operation(
            summary = "Получение Excel файла с данными",
            description = "Позволяет получить Excel файл, в который были записаны данные"
    )
    public ResponseEntity<InputStreamResource> getFile(@PathVariable @Parameter
            (description = "Идентификатор файла словаря") UUID id) {
        MultipartFile multipartFile = fileStorageService.getFile(id);

        // Service
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







    @GetMapping(value = "/exportDictionary")
    @Operation(
            summary = "Экспорт словаря в Excel",
            description = "Позволяет экспортировать данные из словаря в Excel"
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

        // Service
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

    @GetMapping(value = "/exportLanguage")
    @Operation(
            summary = "Экспорт языков в Excel",
            description = "Позволяет экспортировать данные о языках в Excel"
    )
    public GUIDResultModel exportLanguage() {
        return exportLanguagesService.exportLanguage();
    }

    @GetMapping(value = "/getExportLanguage/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Operation(
            summary = "Получение Excel файла с данными языков",
            description = "Позволяет получить Excel файл, в который были записаны данные языков"
    )
    public ResponseEntity<InputStreamResource> getExportLanguage(@PathVariable @Parameter
            (description = "Идентификатор файла языка") UUID id) {
        MultipartFile multipartFile = exportLanguagesService.getExportLanguage(id);

        // Service
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
