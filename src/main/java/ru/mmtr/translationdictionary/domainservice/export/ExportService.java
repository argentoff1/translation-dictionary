package ru.mmtr.translationdictionary.domainservice.export;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import ru.mmtr.translationdictionary.domain.export.ExportCreateModel;
import ru.mmtr.translationdictionary.domain.export.ExportType;
import ru.mmtr.translationdictionary.domainservice.dictionary.DictionaryService;
import ru.mmtr.translationdictionary.domainservice.language.LanguageService;
import ru.mmtr.translationdictionary.domainservice.user.UserService;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: parinos.ma.kst
 * \* Date: 9/11/2023
 * \* Description:
 * \  Сервис для создания файла экспорта
 */

@Service
public class ExportService {
    private final FileStorageService fileStorageService;
    private final Map<ExportType, ExportStrategy> strategies;

    // Стримом сформировать ......toMap по getType(::getType)
    public ExportService(FileStorageService fileStorageService, LanguageService languageService,
                         DictionaryService dictionaryService, UserService userService) {
        this.fileStorageService = fileStorageService;

        strategies = Arrays.stream(ExportType.values())
                .collect(Collectors.toMap(
                        Function.identity(),
                        exportType -> {
                            if (exportType == ExportType.LANGUAGE) {
                                return new LanguageExportStrategy(languageService, userService);
                            } else if (exportType == ExportType.DICTIONARY) {
                                return new DictionaryExportStrategy(languageService, dictionaryService, userService);
                            } else {
                                throw new IllegalArgumentException("Unknown export type: " + exportType);
                            }
                        }
                ));
        //strategies.stream().collect(Collectors.toMap(x -> x.getModelName()))
    }

    public Workbook createExport(ExportCreateModel model) {
        var result = strategies.get(model.getExportType()).createExport(model);

        fileStorageService.createFile(result);

        return result;
    }
}
