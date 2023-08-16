package ru.mmtr.translationdictionary.domainservice.language;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.mmtr.translationdictionary.domain.common.PageModel;
import ru.mmtr.translationdictionary.domain.common.PageResultModel;
import ru.mmtr.translationdictionary.domain.common.SuccessResultModel;
import ru.mmtr.translationdictionary.domain.language.LanguageModel;
import ru.mmtr.translationdictionary.infrastructure.repositories.language.LanguageRepository;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Validated
public class LanguageService {
    private final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public LanguageModel getById(UUID id) {
        LanguageModel languageModel;

        languageModel = languageRepository.getById(id);

        return languageModel;
    }

    public LanguageModel getByName(String languageName) {
        // 500 NullPointerException при ошибке поиска
        if (StringUtils.isBlank(languageName)) {
            // Либо модель с Errorcode, errormessage
            return null;
        }

        return languageRepository.getByName(languageName);
    }

    public List<LanguageModel> showAll() {
        //Pageable pageable = PageRequest.of(page, size);

        //Page<LanguageModel> modelPage = languageRepository.showAll(pageable);

        List<LanguageModel> languageModels = languageRepository.showAll();

        return languageModels;
        //return modelPage.getContent();
    }

    public PageResultModel<LanguageModel> showAllWithPagination(PageModel model) {


        return languageRepository.showAllWithPagination(model);
    }

    public SuccessResultModel save(String languageName) {
        // 400 Bad request
        // 405 HttpRequestMethodNotSupportedException
        // 500 javax.persistence.PersistenceException при введении пустоты и большого кол-ва символов
        // Дает вставлять пробелы
        LanguageModel languageModel = new LanguageModel();
        try {
            //log.info("------Создание языка----------");

            languageModel = languageRepository.save(languageName);

            if (languageName == null) {
                //return new SuccessResultModel();
            }

            if (countChars(languageName) > 15) {
                throw new PersistenceException("Длина языка должна быть менее 15 символов");
            }

            //log.info("-----Язык создан успешно---------");
        } catch (NullPointerException e) {

        }
        // На пустоту, но почему-то Exception не выбрасывается
        // DuplicateKeyException
        // На пробелы, мб нужно кастомное исключение
        // PersistenceException Длина введенного языка
        //return languageModel;
        return null;
    }

    public LanguageModel update(UUID id, String languageName) {
        // 500 NullPointerException при введении несуществующего UUID

        return languageRepository.update(id, languageName);
    }

    public boolean delete(UUID id) {
        boolean isDeleted = false;

        try {
            //log.info("------Получение языка----------");

            // Всегда true
            if (languageRepository.delete(id)) {
                isDeleted = true;
            }

            //log.info("-----Язык получен успешно---------");
            // В catch уже есть отлов искл, не нужен throw new
        } catch (NullPointerException e) {
            //return new
        }

        return isDeleted;
    }

    public static int countChars(String str) {
        int count = 0;
        for (char element : str.toCharArray()) {
            count++;
        }
        return count;
    }
}
