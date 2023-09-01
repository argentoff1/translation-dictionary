package ru.mmtr.translationdictionary.domainservice.export;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.mmtr.translationdictionary.domain.common.PageResultModel;
import ru.mmtr.translationdictionary.domain.dictionary.DictionaryModel;
import ru.mmtr.translationdictionary.domain.dictionary.DictionaryPageRequestModel;
import ru.mmtr.translationdictionary.domain.export.ExportDictionariesModel;
import ru.mmtr.translationdictionary.domain.language.LanguageModel;
import ru.mmtr.translationdictionary.domain.user.UserModel;
import ru.mmtr.translationdictionary.domainservice.common.FileUtils;
import ru.mmtr.translationdictionary.domainservice.common.WriteListToFile;
import ru.mmtr.translationdictionary.domainservice.dictionary.DictionaryService;
import ru.mmtr.translationdictionary.domainservice.language.LanguageService;
import ru.mmtr.translationdictionary.domainservice.user.UserService;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        MultipartFile multipartFile;
        ExportDictionariesModel exportDictionariesModel = new ExportDictionariesModel();

        var dictionaryCriteria = new DictionaryPageRequestModel();
        dictionaryCriteria.setPageNum(0);
        dictionaryCriteria.setPageSize(PAGE_SIZE);
        PageResultModel<DictionaryModel> page;

        List<ExportDictionariesModel> exportDictionariesModels = new ArrayList<>();



        List<String> exportDictionariesModelList = new ArrayList<>();

        List<UUID> fromLanguageList = new ArrayList<>();
        List<UUID> toLanguageList = new ArrayList<>();
        List<String> wordsList = new ArrayList<>();
        List<String> translationsList = new ArrayList<>();
        List<UUID> createdUserIdsList = new ArrayList<>();
        List<LocalDateTime> createdAtList = new ArrayList<>();
        List<UUID> modifiedUserIdsList = new ArrayList<>();
        List<LocalDateTime> modifiedAtList = new ArrayList<>();

        // Обогащение данными из модели
        do {
            page = dictionaryService.getPage(dictionaryCriteria);
            dictionaryCriteria.setPageNum(dictionaryCriteria.getPageNum() + 1);

            for (var model : page.getResultList()) {
                fromLanguageList.add(model.getFromLanguage());
                toLanguageList.add(model.getToLanguage());
                wordsList.add(model.getWord());
                translationsList.add(model.getTranslation());
                createdUserIdsList.add(model.getCreatedUserId());
                createdAtList.add(model.getCreatedAt());
                modifiedUserIdsList.add(model.getModifiedUserId());
                modifiedAtList.add(model.getModifiedAt());
            }
        } while (page.getResultList().size() == PAGE_SIZE);

        // Добавление данных в список для экспорта
        for (int i = 0; i < createdUserIdsList.size(); i++) {
            var model = new ExportDictionariesModel();

            model.setFromLanguage(getLanguagesList(fromLanguageList).get(i));
            model.setToLanguage(getLanguagesList(toLanguageList).get(i));
            model.setWord(wordsList.get(i));
            model.setTranslation(translationsList.get(i));
            model.setFullName(getUsersList(createdUserIdsList).get(i));
            model.setCreatedUserId(createdUserIdsList.get(i));
            model.setCreatedAt(createdAtList.get(i));
            model.setModifiedUserId(modifiedUserIdsList.get(i));
            model.setModifiedAt(modifiedAtList.get(i));

            exportDictionariesModels.add(model);
        }

        /*for (String item : getLanguagesList(fromLanguageList)) {
            exportDictionariesModel.setFromLanguage(item);
        }*/
        //exportDictionariesModels.add(userService.getUsersListByIds(createdUserIdsList));



        /*exportDictionariesModelList.addAll(getLanguagesList(fromLanguageList));
        exportDictionariesModelList.addAll(getLanguagesList(toLanguageList));
        exportDictionariesModelList.addAll(wordsList);
        exportDictionariesModelList.addAll(translationsList);
        exportDictionariesModelList.addAll(getUsersList(createdUserIdsList));
        exportDictionariesModelList.addAll(addDateTimeToResultList(createdAtList));
        exportDictionariesModelList.addAll(getUsersList(modifiedUserIdsList));
        exportDictionariesModelList.addAll(addDateTimeToResultList(modifiedAtList));*/

        // Запись + конвертация в файл
        /*try {
            multipartFile = FileUtils.convertDataFromListToFile(exportDictionariesModelList);

            FileUtils.saveMultipartFile(multipartFile, "C:\\Users\\parinos.ma.kst\\Export\\data.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        try {
            WriteListToFile.writeExportListToFile("C:\\Users\\parinos.ma.kst\\Export\\ExportData.xlsx", exportDictionariesModels);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*// Преобразование списка в модель для экспорта
        return getModel(exportDictionariesModelList);*/

        return null;
    }

    private List<String> addDateTimeToResultList(List<LocalDateTime> dataList) {
        List<String> exportDictionariesModelList = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        for (LocalDateTime item : dataList) {
            try {
                exportDictionariesModelList.add(item.format(formatter));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                exportDictionariesModelList.add(String.valueOf(item));
            }
        }

        return exportDictionariesModelList;
    }

    private List<String> getUsersList(List<UUID> userIdsArrayList) {
        List<String> usersList = new ArrayList<>();

        for (UUID item : userIdsArrayList) {
            UserModel user = userService.getUserById(item);

            usersList.add(user.getLastName() + " " + user.getFirstName() + " " + user.getFatherName());
        }

        return usersList;
    }

    private List<String> getLanguagesList(List<UUID> languageIdsArrayList) {
        List<String> languagesList = new ArrayList<>();

        for (UUID item : languageIdsArrayList) {
            LanguageModel language = languageService.getById(item);

            languagesList.add(language.getLanguageName());
        }

        return languagesList;
    }

    private ExportDictionariesModel getModel(List<String> dataList) {
        var model = new ExportDictionariesModel();
        /*model.setFromLanguage(dataList.get());
        model.setToLanguage(dataList.get());
        model.setFullName(dataList.get());
        model.setEmail(dataList.get());
        model.setCreatedAt(dataList.get());
        model.setModifiedAt(dataList.get());*/

        return model;
    }
}
