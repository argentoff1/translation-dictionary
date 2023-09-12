package ru.mmtr.translationdictionary.domainservice.export.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.mmtr.translationdictionary.domain.common.PageResultModel;
import ru.mmtr.translationdictionary.domain.export.ExportCreateModel;
import ru.mmtr.translationdictionary.domain.export.ExportLanguageModel;
import ru.mmtr.translationdictionary.domain.export.ExportType;
import ru.mmtr.translationdictionary.domain.language.LanguageModel;
import ru.mmtr.translationdictionary.domain.language.LanguagePageRequestModel;
import ru.mmtr.translationdictionary.domain.user.UserModel;
import ru.mmtr.translationdictionary.domainservice.common.WriteListToFile;
import ru.mmtr.translationdictionary.domainservice.export.ExportStrategy;
import ru.mmtr.translationdictionary.domainservice.language.LanguageService;
import ru.mmtr.translationdictionary.domainservice.user.UserService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: parinos.ma.kst
 * \* Date: 9/11/2023
 * \* Description:
 * \  Реализация методов для языков
 */

@Slf4j
@Service
public class LanguageExportStrategy implements ExportStrategy {
    private final LanguageService languageService;
    private final UserService userService;
    private static final int PAGE_SIZE = 100;

    public LanguageExportStrategy(LanguageService languageService, UserService userService) {
        this.languageService = languageService;
        this.userService = userService;
    }

    @Override
    public Workbook createExport(ExportCreateModel model) {
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

            WriteListToFile.workbookLanguageCreateHeadersIfRequired(workbook);

            try {
                WriteListToFile.fillLanguageWorkbook(exportLanguageModels, workbook);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        } while (page.getResultList().size() == PAGE_SIZE);

        return workbook;
    }

    @Override
    public ExportType getType() {
        return ExportType.LANGUAGE;
    }
}
