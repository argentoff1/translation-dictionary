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

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static ru.mmtr.translationdictionary.domainservice.common.Validation.isValidUUID;
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

    public Map<UUID, LanguageModel> getByIds(List<UUID> idList) {
        return languageRepository.getByIds(idList);
    }

    public PageResultModel<LanguageModel> getPage(LanguagePageRequestModel criteria) {
        return languageRepository.getPage(criteria);
    }

    public LanguageModel getById(UUID id) {
        if (!isValidUUID(String.valueOf(id))) {
            return new LanguageModel("CAN_NOT_UPDATE",
                    "Не удалось обновить данные. Поля должны быть корректно заполнены");
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

        return languageRepository.save(model);
    }

    public SuccessResultModel update(LanguageUpdateModel model) {
        if (!isValidUUID(String.valueOf(model.getId()))) {
            return new SuccessResultModel("CAN_NOT_UPDATE",
                    "Не удалось обновить данные. Поля должны быть корректно заполнены");
        }

        var validationResult = stringValidation(model.getLanguageName(), 15);

        if (validationResult.getErrorCode() != null) {
            return validationResult;
        }

        languageRepository.update(model);

        return new SuccessResultModel(true);
    }

    public SuccessResultModel delete(UUID id) {
        if (!isValidUUID(String.valueOf(id))) {
            return new SuccessResultModel("CAN_NOT_UPDATE",
                    "Не удалось обновить данные. Поля должны быть корректно заполнены");
        }

        languageRepository.delete(id);

        return new SuccessResultModel(true);
    }
}
