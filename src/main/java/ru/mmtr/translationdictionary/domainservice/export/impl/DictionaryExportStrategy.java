package ru.mmtr.translationdictionary.domainservice.export.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.mmtr.translationdictionary.domain.common.PageResultModel;
import ru.mmtr.translationdictionary.domain.dictionary.DictionaryModel;
import ru.mmtr.translationdictionary.domain.dictionary.DictionaryPageRequestModel;
import ru.mmtr.translationdictionary.domain.export.ExportCreateModel;
import ru.mmtr.translationdictionary.domain.export.ExportDictionaryModel;
import ru.mmtr.translationdictionary.domain.export.ExportType;
import ru.mmtr.translationdictionary.domain.language.LanguageModel;
import ru.mmtr.translationdictionary.domain.user.UserModel;
import ru.mmtr.translationdictionary.domainservice.common.WriteListToFile;
import ru.mmtr.translationdictionary.domainservice.dictionary.DictionaryService;
import ru.mmtr.translationdictionary.domainservice.export.ExportStrategy;
import ru.mmtr.translationdictionary.domainservice.language.LanguageService;
import ru.mmtr.translationdictionary.domainservice.user.UserService;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DictionaryExportStrategy implements ExportStrategy {
    private final LanguageService languageService;
    private final DictionaryService dictionaryService;
    private final UserService userService;
    private static final int PAGE_SIZE = 100;

    public DictionaryExportStrategy(LanguageService languageService, DictionaryService dictionaryService, UserService userService) {
        this.languageService = languageService;
        this.dictionaryService = dictionaryService;
        this.userService = userService;
    }

    @Override
    public Workbook createExport(ExportCreateModel model) {
        var dictionaryCriteria = new DictionaryPageRequestModel();
        dictionaryCriteria.setPageNum(0);
        dictionaryCriteria.setPageSize(PAGE_SIZE);
        PageResultModel<DictionaryModel> page;

        // Обогащение пагинации данными из модели
        List<ExportDictionaryModel> exportDictionaryModels;
        Workbook workbook = new XSSFWorkbook();
        do {
            page = dictionaryService.getPage(dictionaryCriteria);
            dictionaryCriteria.setPageNum(dictionaryCriteria.getPageNum() + 1);

            // Экспорт данных из пагинации в модель
            exportDictionaryModels = page.getResultList().stream().map(dictionaryModel -> {
                var exportDictionaryModel = new ExportDictionaryModel();
                // Переложить все поля в модель из dictionaryModel
                exportDictionaryModel.setFromLanguageUUID(dictionaryModel.getFromLanguage());
                exportDictionaryModel.setToLanguageUUID(dictionaryModel.getToLanguage());
                exportDictionaryModel.setWord(dictionaryModel.getWord());
                exportDictionaryModel.setTranslation(dictionaryModel.getTranslation());
                exportDictionaryModel.setCreatedUserId(dictionaryModel.getCreatedUserId());
                exportDictionaryModel.setCreatedAt(dictionaryModel.getCreatedAt());
                exportDictionaryModel.setModifiedUserId(dictionaryModel.getModifiedUserId());
                exportDictionaryModel.setModifiedAt(dictionaryModel.getModifiedAt());

                return exportDictionaryModel;
            }).toList();

            var languageFromIds = exportDictionaryModels.stream()
                    .map(ExportDictionaryModel::getFromLanguageUUID)
                    .distinct().filter(Objects::nonNull)
                    .collect(Collectors.toList());
            var languageToIds = exportDictionaryModels.stream()
                    .map(ExportDictionaryModel::getToLanguageUUID)
                    .distinct().filter(Objects::nonNull)
                    .collect(Collectors.toList());
            var userCreatorsIds = exportDictionaryModels.stream()
                    .map(ExportDictionaryModel::getCreatedUserId)
                    .distinct().filter(Objects::nonNull)
                    .collect(Collectors.toList());
            var userModifiersIds = exportDictionaryModels.stream()
                    .map(ExportDictionaryModel::getModifiedUserId)
                    .distinct().filter(Objects::nonNull)
                    .collect(Collectors.toList());

            // Вызывать метод из репозитория, который вернет модели. На вход список GUID
            // получить все остальные сущности, остальные ID
            Map<UUID, LanguageModel> fromLanguageNamesMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(languageFromIds)) {
                fromLanguageNamesMap = languageService.getByIds(languageFromIds);
            }

            Map<UUID, LanguageModel> toLanguageNamesMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(languageToIds)) {
                toLanguageNamesMap = languageService.getByIds(languageToIds);
            }

            Map<UUID, UserModel> userCreatorsMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(userCreatorsIds)) {
                userCreatorsMap = userService.getByIds(userCreatorsIds);
            }

            Map<UUID, UserModel> userModifiersMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(userModifiersIds)) {
                userModifiersMap = userService.getByIds(userModifiersIds);
            }

            // Положить Map в модель
            for (var exportDictionariesModel : exportDictionaryModels) {
                var fromLanguageName = fromLanguageNamesMap.get(exportDictionariesModel.getFromLanguageUUID());
                if (fromLanguageName != null) {
                    exportDictionariesModel.setFromLanguageName(fromLanguageName.getLanguageName());
                }

                var toLanguageName = toLanguageNamesMap.get(exportDictionariesModel.getToLanguageUUID());
                if (toLanguageName != null) {
                    exportDictionariesModel.setToLanguageName(toLanguageName.getLanguageName());
                }

                var userCreator = userCreatorsMap.get(exportDictionariesModel.getCreatedUserId());
                if (userCreator != null) {
                    exportDictionariesModel.setFullName(userCreator.getLastName() + " "
                            + userCreator.getFirstName());
                }

                var userModifier = userModifiersMap.get(exportDictionariesModel.getModifiedUserId());
                if (userModifier != null) {
                    exportDictionariesModel.setFullName(userModifier.getLastName() + " "
                            + userModifier.getFirstName());
                }
            }

            WriteListToFile.workbookDictionaryCreateHeadersIfRequired(exportDictionaryModels, workbook);

            try {
                WriteListToFile.fillDictionaryWorkbook(exportDictionaryModels, workbook);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        } while (page.getResultList().size() == PAGE_SIZE);

        return workbook;
    }

    @Override
    public ExportType getType() {
        return ExportType.DICTIONARY;
    }
}
