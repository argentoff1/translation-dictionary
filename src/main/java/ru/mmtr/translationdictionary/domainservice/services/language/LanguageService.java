package ru.mmtr.translationdictionary.domainservice.services.language;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mmtr.translationdictionary.domain.models.language.LanguageModel;
import ru.mmtr.translationdictionary.domainservice.services.EntityNotFoundException;
import ru.mmtr.translationdictionary.infrastructure.repositories.language.LanguageRepository;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class LanguageService {
    @Autowired
    private LanguageRepository languageRepository;

    public LanguageModel getLanguage(UUID id) {
        LanguageModel languageModel;
        try {
            log.info("------Получение языка----------");

            languageModel = languageRepository.getLanguage(id);

            log.info("-----Язык получен успешно---------");
        } catch (NullPointerException e) {
            throw new NullPointerException("Словарь или язык не найден");
        }
        return languageModel;
    }

    public List<LanguageModel> getAllLanguages() {




        List<LanguageModel> languageModels = languageRepository.getAllLanguages();

        return languageModels;
    }

    public LanguageModel createLanguage(String languageName) {
        LanguageModel languageModel = new LanguageModel();
        try {
            log.info("------Создание языка----------");

            languageModel = languageRepository.createLanguage(languageName);

            if (languageName == null) {
                throw new NullPointerException("Название должно быть заполнено");
            }

            if (countChars(languageName) > 15) {
                throw new PersistenceException("Длина языка должна быть менее 15 символов");
            }

            log.info("-----Язык создан успешно---------");
        }




        catch (NullPointerException e) {

        }
        // На пустоту, но почему-то Exception не выбрасывается
        // DuplicateKeyException
        // На пробелы, мб нужно кастомное исключение
        // PersistenceException Длина введенного языка
        return languageModel;
    }

    public String saveLanguage(UUID id, String languageName) {




        int savedRows = languageRepository.saveLanguage(id, languageName);

        return "Было обновлено " + savedRows + " строк";
    }

    public String deleteLanguage(UUID id) {
        int deletedRows = 0;
        try {
            log.info("------Получение языка----------");

            deletedRows = languageRepository.deleteLanguage(id);

            log.info("-----Язык получен успешно---------");
        } catch (NullPointerException e) {
            throw new NullPointerException("Словарь или язык не найден");
        }

        return "Было удалено " + deletedRows + " строк";
    }

    public static int countChars(String str) {
        int count = 0;
        for (char element : str.toCharArray()) {
            count++;
        }
        return count;
    }
}
