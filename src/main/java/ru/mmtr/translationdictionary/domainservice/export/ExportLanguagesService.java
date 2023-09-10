package ru.mmtr.translationdictionary.domainservice.export;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.mmtr.translationdictionary.domain.common.GUIDResultModel;
import ru.mmtr.translationdictionary.domain.common.PageResultModel;
import ru.mmtr.translationdictionary.domain.export.ExportLanguageModel;
import ru.mmtr.translationdictionary.domain.language.LanguageModel;
import ru.mmtr.translationdictionary.domain.language.LanguagePageRequestModel;
import ru.mmtr.translationdictionary.domain.user.UserModel;
import ru.mmtr.translationdictionary.domainservice.common.WriteListToFile;
import ru.mmtr.translationdictionary.domainservice.language.LanguageService;
import ru.mmtr.translationdictionary.domainservice.user.UserService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: parinos.ma.kst
 * \* Date: 9/8/2023
 * \* Description:
 * \
 */
@Service
@Slf4j
public class ExportLanguagesService {
    private final LanguageService languageService;
    private final UserService userService;
    private static final int PAGE_SIZE = 100;
    public static final String LANGUAGES_FILE_PATH = "C:\\Users\\parinos.ma.kst\\IdeaProjects\\" +
            "translation-dictionary\\src\\main\\resources\\export\\languages";

    public ExportLanguagesService(LanguageService languageService, UserService userService) {
        this.languageService = languageService;
        this.userService = userService;
    }

    public GUIDResultModel exportLanguage() {
        var languageCriteria = new LanguagePageRequestModel();
        languageCriteria.setPageNum(0);
        languageCriteria.setPageSize(PAGE_SIZE);
        PageResultModel<LanguageModel> page;

        List<ExportLanguageModel> exportLanguageModels;
        Workbook workbook = new XSSFWorkbook();
        do {
            page = languageService.getPage(languageCriteria);
            languageCriteria.setPageNum(languageCriteria.getPageNum() + 1);

            exportLanguageModels = page.getResultList().stream().map(languageModel -> {
                var exportLanguageModel = new ExportLanguageModel();
                exportLanguageModel.setLanguageId(languageModel.getLanguageId());
                exportLanguageModel.setLanguageName(languageModel.getLanguageName());
                exportLanguageModel.setCreatedAt(languageModel.getCreatedAt());
                exportLanguageModel.setModifiedAt(languageModel.getModifiedAt());
                exportLanguageModel.setCreatedUserId(languageModel.getCreatedUserId());
                exportLanguageModel.setModifiedUserId(languageModel.getModifiedUserId());

                return exportLanguageModel;
            }).toList();

            var userCreatorsIds = exportLanguageModels.stream()
                    .map(ExportLanguageModel::getCreatedUserId)
                    .distinct().filter(Objects::nonNull)
                    .collect(Collectors.toList());
            var userModifiersIds = exportLanguageModels.stream()
                    .map(ExportLanguageModel::getModifiedUserId)
                    .distinct().filter(Objects::nonNull)
                    .collect(Collectors.toList());


            Map<UUID, UserModel> userCreatorsMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(userCreatorsIds)) {
                userCreatorsMap = userService.getByIds(userCreatorsIds);
            }

            Map<UUID, UserModel> userModifiersMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(userModifiersIds)) {
                userModifiersMap = userService.getByIds(userModifiersIds);
            }


            for (var exportLanguageModel : exportLanguageModels) {
                var userCreator = userCreatorsMap.get(exportLanguageModel.getCreatedUserId());
                if (userCreator != null) {
                    exportLanguageModel.setFullName(userCreator.getLastName() + " "
                            + userCreator.getFirstName());
                }

                var userModifier = userModifiersMap.get(exportLanguageModel.getModifiedUserId());
                if (userModifier != null) {
                    exportLanguageModel.setFullName(userModifier.getLastName() + " "
                            + userModifier.getFirstName());
                }
            }

            WriteListToFile.workbookLanguageCreateHeadersIfRequired(exportLanguageModels, workbook);

            try {
                WriteListToFile.fillLanguageWorkbook(exportLanguageModels, workbook);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return new GUIDResultModel("CAN_NOT_EXPORT",
                        "Не удалось экспортировать данные");
            }
        } while (page.getResultList().size() == PAGE_SIZE);

        return new GUIDResultModel(WriteListToFile.writeInFile(LANGUAGES_FILE_PATH, workbook));
    }

    public MultipartFile getExportLanguage(UUID id) {
        try {
            File file = new File(LANGUAGES_FILE_PATH + id + ".xlsx");
            if (!file.exists()) {
                log.error("Файла по указанному пути не существует");
            }
            FileInputStream input = new FileInputStream(file);

            return new MockMultipartFile("C:/Users/parinos.ma.kst/IdeaProjects/" +
                    "translation-dictionary/src/main/resources/export", file.getName(),
                    "multipart/form-data", IOUtils.toByteArray(input));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }
}