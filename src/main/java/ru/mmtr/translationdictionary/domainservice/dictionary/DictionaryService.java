package ru.mmtr.translationdictionary.domainservice.dictionary;

import org.springframework.stereotype.Service;
import ru.mmtr.translationdictionary.domain.common.*;
import ru.mmtr.translationdictionary.domain.dictionary.*;
import ru.mmtr.translationdictionary.infrastructure.repositories.dictionary.DictionaryRepository;

import static ru.mmtr.translationdictionary.domainservice.common.Validation.isValidUUID;
import static ru.mmtr.translationdictionary.domainservice.common.Validation.stringValidation;

import java.util.UUID;

@Service
public class DictionaryService {
    private final DictionaryRepository dictionaryRepository;

    public DictionaryService(DictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }

    public CollectionResultModel<DictionaryModel> showAll() {
        return dictionaryRepository.showAll();
    }

    public PageResultModel<DictionaryModel> getPage(DictionaryPageRequestModel criteria) {
        return dictionaryRepository.getPage(criteria);
    }

    public DictionaryModel getById(UUID id) {
        if (!isValidUUID(String.valueOf(id))) {
            return new DictionaryModel("CAN_NOT_FIND",
                    "Не удалось найти словарь");
        }

        return dictionaryRepository.getById(id);
    }

    public StringResultModel getTranslatedWord(DictionaryTranslateModel model) {
        var validationResult = stringValidation(model.getWord(), 100);

        if (validationResult.getErrorCode() != null) {
            return new StringResultModel("CAN_NOT_FIND",
                    "Не удалось найти данные. Поля должны быть корректно заполненными");
        }

        if (model.getFromLanguage() == null || model.getToLanguage() == null) {
            return new StringResultModel("CAN_NOT_FIND",
                    "Не удалось найти данные. Поля должны быть корректно заполненными");
        }

        return dictionaryRepository.getTranslatedWord(model);
    }

    public GUIDResultModel save(DictionarySaveModel model) {
        var validationResult = stringValidation(model.getWord(), 100);
        if (validationResult.getErrorCode() != null) {
            return new GUIDResultModel("CAN_NOT_SAVE",
                    "Не удалось сохранить данные. Поля должны быть заполненными");
        }

        validationResult = stringValidation(model.getTranslation(), 100);
        if (validationResult.getErrorCode() != null) {
            return new GUIDResultModel("CAN_NOT_SAVE",
                    "Не удалось сохранить данные. Поля должны быть заполненными");
        }

        if (model.getFromLanguage() == null || model.getToLanguage() == null) {
            return new GUIDResultModel("CAN_NOT_FIND",
                    "Не удалось найти данные. Поля должны быть корректно заполненными");
        }

        return dictionaryRepository.save(model);
    }

    public SuccessResultModel update(DictionaryUpdateModel model) {
        if (!isValidUUID(String.valueOf(model.getId()))) {
            return new SuccessResultModel("CAN_NOT_UPDATE",
                    "Не удалось обновить данные. Поля должны быть корректно заполнены");
        }

        var validationResult = stringValidation(model.getWord(), 100);

        if (validationResult.getErrorCode() != null) {
            return validationResult;
        }

        validationResult = stringValidation(model.getTranslation(), 100);

        if (validationResult.getErrorCode() != null) {
            return validationResult;
        }

        var repositoryResult = dictionaryRepository.update(model);

        if (repositoryResult == null) {
            return new SuccessResultModel("CAN_NOT_UPDATE",
                    "Не удалось сохранить данные. Поля должны быть корректно заполненными");
        }

        return new SuccessResultModel(true);
    }

    public SuccessResultModel delete(UUID id) {
        if (!isValidUUID(String.valueOf(id))) {
            return new SuccessResultModel("CAN_NOT_UPDATE",
                    "Не удалось обновить данные. Поля должны быть корректно заполнены");
        }

        dictionaryRepository.delete(id);

        return new SuccessResultModel(true);
    }
}
