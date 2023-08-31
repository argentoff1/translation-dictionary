package ru.mmtr.translationdictionary.domainservice.export;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.mmtr.translationdictionary.domain.common.PageResultModel;
import ru.mmtr.translationdictionary.domain.dictionary.DictionaryModel;
import ru.mmtr.translationdictionary.domain.dictionary.DictionaryPageRequestModel;
import ru.mmtr.translationdictionary.domain.export.ExportDictionariesModel;
import ru.mmtr.translationdictionary.domain.language.LanguageModel;
import ru.mmtr.translationdictionary.domain.user.UserModel;
import ru.mmtr.translationdictionary.domainservice.common.WriteListToFile;
import ru.mmtr.translationdictionary.domainservice.dictionary.DictionaryService;
import ru.mmtr.translationdictionary.domainservice.language.LanguageService;
import ru.mmtr.translationdictionary.domainservice.user.UserService;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

import static ru.mmtr.translationdictionary.domainservice.common.FileUtils.convertDataFromListToFile;
import static ru.mmtr.translationdictionary.domainservice.common.FileUtils.saveMultipartFile;

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

        var dictionaryCriteria = new DictionaryPageRequestModel();
        dictionaryCriteria.setPageNum(0);
        dictionaryCriteria.setPageSize(PAGE_SIZE);
        PageResultModel<DictionaryModel> page;

        ArrayList<String> exportDictionariesModelList = new ArrayList<>();

        ArrayList<UUID> fromLanguageList = new ArrayList<>();
        ArrayList<UUID> toLanguageList = new ArrayList<>();
        ArrayList<String> wordsList = new ArrayList<>();
        ArrayList<String> translationsList = new ArrayList<>();
        ArrayList<UUID> createdUserIdsList = new ArrayList<>();
        ArrayList<LocalDateTime> createdAtList = new ArrayList<>();
        ArrayList<UUID> modifiedUserIdsList = new ArrayList<>();
        ArrayList<LocalDateTime> modifiedAtList = new ArrayList<>();

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

        // Добавление данных в список для экспорта. Цикл приоритетнее
        exportDictionariesModelList.addAll(getLanguagesList(fromLanguageList));
        exportDictionariesModelList.addAll(getLanguagesList(toLanguageList));
        exportDictionariesModelList.addAll(wordsList);
        exportDictionariesModelList.addAll(translationsList);
        exportDictionariesModelList.addAll(getUsersList(createdUserIdsList));
        exportDictionariesModelList.addAll(addDateTimeToResultList(createdAtList));
        exportDictionariesModelList.addAll(getUsersList(modifiedUserIdsList));
        exportDictionariesModelList.addAll(addDateTimeToResultList(modifiedAtList));

        // Запись + конвертация в файл
        try {
            multipartFile = convertDataFromListToFile(exportDictionariesModelList);

            saveMultipartFile(multipartFile, "C:\\Users\\parinos.ma.kst\\Export\\data.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            WriteListToFile.writeExportListToFile("Export", exportDictionariesModelList);
        } catch (Exception e) {
            e.printStackTrace();
        }

         /*Преобразование списка в модель для экспорта
        return getModel(exportDictionariesModelList);*/

        return null;
    }

    private ArrayList<String> addDataToResultList(ArrayList<String> dataList) {
        ArrayList<String> exportDictionariesModelList = new ArrayList<>();

        for (String item : dataList) {
            exportDictionariesModelList.add(item);
        }

        return exportDictionariesModelList;
    }

    private ArrayList<String> addDateTimeToResultList(ArrayList<LocalDateTime> dataList) {
        ArrayList<String> exportDictionariesModelList = new ArrayList<>();

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

    private ArrayList<String> getUsersList(ArrayList<UUID> userIdsArrayList) {
        ArrayList<String> usersList = new ArrayList<>();

        for (UUID item : userIdsArrayList) {
            UserModel user = userService.getUserById(item);

            usersList.add(user.getLastName() + " " + user.getFirstName() + " " + user.getFatherName());
        }

        return usersList;
    }

    private ArrayList<String> getLanguagesList(ArrayList<UUID> languageIdsArrayList) {
        ArrayList<String> languagesList = new ArrayList<>();

        for (UUID item : languageIdsArrayList) {
            LanguageModel language = languageService.getById(item);

            languagesList.add(language.getLanguageName());
        }

        return languagesList;
    }

    private ExportDictionariesModel getModel(ArrayList<String> dataList) {
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
