package ru.mmtr.translationdictionary.domainservice.export;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.mmtr.translationdictionary.domain.common.GUIDResultModel;
import ru.mmtr.translationdictionary.domain.common.PageResultModel;
import ru.mmtr.translationdictionary.domain.common.SuccessResultModel;
import ru.mmtr.translationdictionary.domain.dictionary.DictionaryModel;
import ru.mmtr.translationdictionary.domain.dictionary.DictionaryPageRequestModel;
import ru.mmtr.translationdictionary.domain.export.ExportDictionariesModel;
import ru.mmtr.translationdictionary.domain.language.LanguageModel;
import ru.mmtr.translationdictionary.domain.user.UserModel;
import ru.mmtr.translationdictionary.domainservice.common.WriteListToFile;
import ru.mmtr.translationdictionary.domainservice.dictionary.DictionaryService;
import ru.mmtr.translationdictionary.domainservice.language.LanguageService;
import ru.mmtr.translationdictionary.domainservice.user.UserService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static ru.mmtr.translationdictionary.domainservice.common.Validation.isValidUUID;

@Service
@Slf4j
public class ExportDictionariesService {
    private final Integer PAGE_SIZE = 100;
    private final LanguageService languageService;
    private final DictionaryService dictionaryService;
    private final UserService userService;
    private UUID dictionaryExportFileUUID;
    private String dictionaryExportFileName;

    public ExportDictionariesService(LanguageService languageService, DictionaryService dictionaryService, UserService userService) {
        this.languageService = languageService;
        this.dictionaryService = dictionaryService;
        this.userService = userService;
    }

    public GUIDResultModel exportDictionary() {
        var dictionaryCriteria = new DictionaryPageRequestModel();
        dictionaryCriteria.setPageNum(0);
        dictionaryCriteria.setPageSize(PAGE_SIZE);
        PageResultModel<DictionaryModel> page;

        // Обогащение пагинации данными из модели
        do {
            page = dictionaryService.getPage(dictionaryCriteria);
            dictionaryCriteria.setPageNum(dictionaryCriteria.getPageNum() + 1);

            // Экспорт данных из пагинации в модель
            var internalPage = page.getResultList().stream().map(dictionaryModel -> {

                var exportDictionaryModel = new ExportDictionariesModel();
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

            var languageFromIds = internalPage.stream()
                    .map(ExportDictionariesModel::getFromLanguageUUID)
                    .distinct().filter(Objects::nonNull)
                    .collect(Collectors.toList());
            var languageToIds = internalPage.stream()
                    .map(ExportDictionariesModel::getToLanguageUUID)
                    .distinct().filter(Objects::nonNull)
                    .collect(Collectors.toList());
            var userCreatorsIds = internalPage.stream()
                    .map(ExportDictionariesModel::getCreatedUserId)
                    .distinct().filter(Objects::nonNull)
                    .collect(Collectors.toList());
            var userModifiersIds = internalPage.stream()
                    .map(ExportDictionariesModel::getModifiedUserId)
                    .distinct().filter(Objects::nonNull)
                    .collect(Collectors.toList());

            // вызывать метод из репозитория, который вернет модели. на вход список guid
            // Получить все остальные сущности, остальные ID
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

            // Засунуть мапы в модель
            for (var exportDictionariesModel : internalPage) {
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

            try {
                dictionaryExportFileUUID = UUID.randomUUID();
                // Полный путь до директории
                //WriteListToFile.writeExportListToFile("resources\\export\\" + dictionaryExportFileUUID + ".xlsx", internalPage);
                WriteListToFile.writeExportListToFile("C:\\Users\\parinos.ma.kst\\IdeaProjects\\translation-dictionary\\src\\main\\resources\\export\\" + dictionaryExportFileUUID + ".xlsx", internalPage);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return new GUIDResultModel("CAN_NOT_EXPORT",
                        "Не удалось экспортировать данные");
            }
        } while (page.getResultList().size() == PAGE_SIZE);

        // Преобразование списка в модель для экспорта
        // GUID - название файла
        return new GUIDResultModel(dictionaryExportFileUUID);
    }

    // Проверка названия файла до точки
    public ExportDictionariesModel getExportDictionary(UUID id) {
        if (!isValidUUID(String.valueOf(id))) {
            return new ExportDictionariesModel("CAN_NOT_UPDATE",
                    "Не удалось обновить данные. Поля должны быть корректно заполнены");
        }

        File file = new File("./" + id + ".xlsx");
        if (file == null) {
            return new ExportDictionariesModel("CAN_NOT_FIND_FILE",
                    "Не удалось найти файл по данному идентификатору");
        }

        try (FileInputStream fileInputStream = new FileInputStream(file);
             Workbook workbook = WorkbookFactory.create(fileInputStream)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                for (Cell cell : row) {
                    CellType cellType = cell.getCellType();
                    if (cellType == CellType.STRING) {
                        System.out.print(cell.getStringCellValue() + "\t");
                    } else if (cellType == CellType.NUMERIC) {
                        System.out.print(cell.getNumericCellValue() + "\t");
                    } else if (cellType == CellType.BLANK) {
                        System.out.print("\t");
                    }
                }
                System.out.println();
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private Boolean checkFileName(String fileName, UUID id) {

        return id.toString().equals(fileName);
    }

    /*private ExportDictionariesModel getModel(List<ExportDictionariesModel> dataList) {
        var model = new ExportDictionariesModel();
        model.setFromLanguage(dataList.get());
        model.setToLanguage(dataList.get());
        model.setFullName(dataList.get());
        model.setEmail(dataList.get());
        model.setCreatedAt(dataList.get());
        model.setModifiedAt(dataList.get());

        return model;
    }*/
}
