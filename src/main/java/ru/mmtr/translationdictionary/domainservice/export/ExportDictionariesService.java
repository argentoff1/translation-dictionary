package ru.mmtr.translationdictionary.domainservice.export;

import org.springframework.stereotype.Service;
import ru.mmtr.translationdictionary.domain.common.PageResultModel;
import ru.mmtr.translationdictionary.domain.dictionary.DictionaryModel;
import ru.mmtr.translationdictionary.domain.dictionary.DictionaryPageRequestModel;
import ru.mmtr.translationdictionary.domain.export.ExportDictionariesModel;
import ru.mmtr.translationdictionary.domainservice.common.WriteListToFile;
import ru.mmtr.translationdictionary.domainservice.dictionary.DictionaryService;
import ru.mmtr.translationdictionary.domainservice.language.LanguageService;
import ru.mmtr.translationdictionary.domainservice.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExportDictionariesService {
    private final Integer PAGE_SIZE = 100;
    private final LanguageService languageService;
    private final DictionaryService dictionaryService;
    private final UserService userService;

    public ExportDictionariesService(LanguageService languageService, DictionaryService dictionaryService, UserService userService) {
        this.languageService = languageService;
        this.dictionaryService = dictionaryService;
        this.userService = userService;
    }

    public ExportDictionariesModel exportDictionary() {
        var dictionaryCriteria = new DictionaryPageRequestModel();
        dictionaryCriteria.setPageNum(0);
        dictionaryCriteria.setPageSize(PAGE_SIZE);
        PageResultModel<DictionaryModel> page;

        List<ExportDictionariesModel> exportDictionariesModels = new ArrayList<>();

        // Обогащение данными из модели
        do {
            page = dictionaryService.getPage(dictionaryCriteria);
            dictionaryCriteria.setPageNum(dictionaryCriteria.getPageNum() + 1);
            // Экспорт данных из пагинации в модель
            var internalPage = page.getResultList().stream().map(dictionaryModel -> {

                var exportDictionaryModel = new ExportDictionariesModel();
                // Переложить все поля в модель из dictionaryModel в exportDictionaryModel
                return exportDictionaryModel;
            }).collect(Collectors.toList());
            var userCreatorsIds = internalPage.stream().map(ExportDictionariesModel::getCreatedUserId).distinct().collect(Collectors.toList());
            var userModifiersIds = internalPage.stream().map(ExportDictionariesModel::getModifiedUserId).distinct().collect(Collectors.toList());
            // вызывать метод из репозитория, который вернет модели. на вход список guid
            var languageMap = languageService.getByIds(userCreatorsIds);
            // Получить все остальные сущности, остальные ID
            var userCreatorsMap = userService.getByIds(userCreatorsIds);
            var userModifiedMap = userService.getByIds(userModifiersIds);
            // Засунуть мапы в модель
            internalPage.forEach(exportDictionariesModel -> {
                internalPage.stream().map(ExportDictionariesModel::getFromLanguage).collect(Collectors.toList());
            });
        } while (page.getResultList().size() == PAGE_SIZE);

        try {
            WriteListToFile.writeExportListToFile("ExportData.xlsx", exportDictionariesModels);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

        // Преобразование списка в модель для экспорта
        //return getModel(exportDictionariesModels);
    }

    /*private ExportDictionariesModel getModel(List<ExportDictionariesModel> dataList) {
        var model = new ExportDictionariesModel();
        model.setFromLanguage(dataList.get);
        model.setToLanguage(dataList.get());
        model.setFullName(dataList.get());
        model.setEmail(dataList.get());
        model.setCreatedAt(dataList.get());
        model.setModifiedAt(dataList.get());

        return model;
    }*/
}
