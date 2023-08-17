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
        checkIsEmpty(languageName);
        checkForSpaces(languageName);

        return languageRepository.getByName(languageName);
    }

    public List<LanguageModel> showAll() {

        return languageRepository.showAll();
    }

    public PageResultModel<LanguageModel> showAllWithPagination(PageModel model) {


        return languageRepository.showAllWithPagination(model);
    }

    public SuccessResultModel save(String languageName) {
        checkIsEmpty(languageName);

        checkForSpaces(languageName);

        if (countChars(languageName) > 15) {
            return new SuccessResultModel("FIELD_VALUE_OUT_OF_BOUNDS", "Поле не должно быть больше 15 символов");
        }

        return languageRepository.save(languageName);
        // 400 Bad request
        // 405 HttpRequestMethodNotSupportedException
        // На пустоту, но почему-то Exception не выбрасывается
        // DuplicateKeyException
    }

    public SuccessResultModel update(UUID id, String languageName) {
        // 500 NullPointerException при введении несуществующего UUID
        checkIsEmpty(languageName);

        checkForSpaces(languageName);

        if (countChars(languageName) > 15) {
            return new SuccessResultModel("FIELD_VALUE_OUT_OF_BOUNDS", "Поле не должно быть больше 15 символов");
        }

        return languageRepository.update(id, languageName);
    }

    public SuccessResultModel delete(UUID id) {



        return languageRepository.delete(id);
    }

    public static int countChars(String str) {
        int count = 0;
        for (char element : str.toCharArray()) {
            count++;
        }
        return count;
    }

    public static void checkIsEmpty(String languageName) {
        if (languageName.isEmpty()) {
            new SuccessResultModel("FIELD_MUST_BE_FILLED", "Поле должно быть заполнено");
            return;
        }
        new SuccessResultModel(true);
    }

    public static void checkForSpaces(String languageName) {
        if (languageName.contains(" ")) {
            new SuccessResultModel("FIELD_MUST_NOT_CONTAIN_SPACES", "Поле не должно содержать пробелов");
            return;
        }
        new SuccessResultModel(true);
    }
}
