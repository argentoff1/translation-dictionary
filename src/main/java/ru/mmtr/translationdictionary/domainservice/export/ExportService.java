package ru.mmtr.translationdictionary.domainservice.export;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import ru.mmtr.translationdictionary.domain.export.ExportCreateModel;
import ru.mmtr.translationdictionary.domain.export.ExportType;
import ru.mmtr.translationdictionary.domainservice.dictionary.DictionaryService;
import ru.mmtr.translationdictionary.domainservice.export.impl.DictionaryExportStrategy;
import ru.mmtr.translationdictionary.domainservice.export.impl.LanguageExportStrategy;
import ru.mmtr.translationdictionary.domainservice.language.LanguageService;
import ru.mmtr.translationdictionary.domainservice.user.UserService;
import ru.mmtr.translationdictionary.infrastructure.FileStoreService;

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
    private final FileStoreService fileStoreService;
    private final Map<ExportType, ExportStrategy> strategies;

    // Стрим сформировать ......toMap по getType(::getType)
    public ExportService(FileStoreService fileStoreService, LanguageService languageService,
                         DictionaryService dictionaryService, UserService userService) {
        this.fileStoreService = fileStoreService;

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

        fileStoreService.createFile(result);

        return result;
    }
}
