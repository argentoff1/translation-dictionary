package ru.mmtr.translationdictionary.domainservice.language;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.mmtr.translationdictionary.domain.common.*;
import ru.mmtr.translationdictionary.domain.language.LanguageModel;
import ru.mmtr.translationdictionary.domain.language.LanguagePageRequestModel;
import ru.mmtr.translationdictionary.domain.language.LanguageSaveModel;
import ru.mmtr.translationdictionary.domain.language.LanguageUpdateModel;
import ru.mmtr.translationdictionary.infrastructure.repositories.language.LanguageRepository;

import java.util.UUID;

@Slf4j
@Service
@Validated
public class LanguageService {
    private final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public CollectionResultModel<LanguageModel> showAll() {

        return languageRepository.showAll();
    }

    public PageResultModel<LanguageModel> getPage(LanguagePageRequestModel criteria) {


        return languageRepository.getPage(criteria);
    }

    public LanguageModel getById(UUID id) {
        if (id == null) {
            return null;
        }

        return languageRepository.getById(id);
    }

    public GUIDResultModel save(LanguageSaveModel model) {
        var validationResult = stringValidation(model.getLanguageName(), 15);

        if (validationResult.getErrorCode() != null) {
            return new GUIDResultModel("CAN_NOT_SAVE",
                    "Не удалось сохранить данные. " +
                            "Поля должны быть корректно заполненными");
        }

        var result = languageRepository.save(model);

        if (result == null) {
            return new GUIDResultModel("CAN_NOT_SAVE",
                    "Не удалось сохранить данные. " +
                            "Поля должны быть корректно заполненными");
        }

        return result;
    }

    public SuccessResultModel update(LanguageUpdateModel model) {
        var validationResult = stringValidation(model.getLanguageName(), 15);

        if (validationResult.getErrorCode() != null) {
            return validationResult;
        }

        Integer repositoryResult = languageRepository.update(model);

        if (repositoryResult == null) {
            return new SuccessResultModel("CAN_NOT_UPDATE",
                    "Не удалось сохранить данные. Поля должны быть заполненными");
        }

        return new SuccessResultModel(true);
    }

    public SuccessResultModel delete(UUID id) {
        Integer repositoryResult = languageRepository.delete(id);

        if (repositoryResult == null) {
            return new SuccessResultModel("CAN_NOT_DELETE",
                    "Не удалось удалить данные. Поля должны быть заполненными");
        }

        return new SuccessResultModel(true);
    }

    private static SuccessResultModel stringValidation(String str, int countChars) {
        if (StringUtils.isBlank(str)) {
            return new SuccessResultModel("FIELD_MUST_BE_FILLED",
                    "Поле должно быть заполнено");
        }

        if (str.contains(" ")) {
            return new SuccessResultModel("FIELD_MUST_NOT_CONTAIN_SPACES",
                    "Поле не должно содержать пробелов");
        }

        int count = 0;
        for (char element : str.toCharArray()) {
            count++;
        }
        if (count > 15) {
            return new SuccessResultModel("FIELD_VALUE_OUT_OF_BOUNDS",
                    "Поле не должно быть больше 15 символов");
        }

        return new SuccessResultModel(true);
    }
}
