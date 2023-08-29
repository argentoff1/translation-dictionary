package ru.mmtr.translationdictionary.domainservice.language;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.mmtr.translationdictionary.domain.common.*;
import ru.mmtr.translationdictionary.domain.language.LanguageModel;
import ru.mmtr.translationdictionary.domain.language.LanguagePageRequestModel;
import ru.mmtr.translationdictionary.domain.language.LanguageSaveModel;
import ru.mmtr.translationdictionary.domain.language.LanguageUpdateModel;
import ru.mmtr.translationdictionary.infrastructure.repositories.language.LanguageRepository;

import java.util.UUID;

import static ru.mmtr.translationdictionary.domainservice.common.Validation.stringValidation;

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

    // В сейве и апдейте добавлять в колонки created_user_id и modified_user_id
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

        var repositoryResult = languageRepository.update(model);

        if (repositoryResult == null) {
            return new SuccessResultModel("CAN_NOT_UPDATE",
                    "Не удалось сохранить данные. Поля должны быть заполненными");
        }

        return new SuccessResultModel(true);
    }

    public SuccessResultModel delete(UUID id) {
        var repositoryResult = languageRepository.delete(id);

        if (repositoryResult == null) {
            return new SuccessResultModel("CAN_NOT_DELETE",
                    "Не удалось удалить данные. Поля должны быть заполненными");
        }

        return new SuccessResultModel(true);
    }
}
