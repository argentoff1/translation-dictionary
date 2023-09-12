package ru.mmtr.translationdictionary.domainservice.dictionary;

import org.springframework.stereotype.Service;
import ru.mmtr.translationdictionary.domain.common.*;
import ru.mmtr.translationdictionary.domain.dictionary.*;
import ru.mmtr.translationdictionary.infrastructure.repositories.dictionary.DictionaryRepository;

import static ru.mmtr.translationdictionary.domainservice.common.Validation.stringValidation;

import java.util.UUID;

@Service
public class DictionaryService {
    private final DictionaryRepository dictionaryRepository;

    public DictionaryService(DictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }

    public CollectionResultModel<DictionaryModel> showAll() {
        var result = dictionaryRepository.showAll();
        if (result == null) {
            return new CollectionResultModel<>("CAN_NOT_SHOW_DICTIONARIES",
                    "Не удалось вывести список всех словарей");
        }

        return result;
    }

    public PageResultModel<DictionaryModel> getPage(DictionaryPageRequestModel criteria) {
        var result = dictionaryRepository.getPage(criteria);
        if (result == null) {
            return new PageResultModel<>("CAN_NOT_RETURN_PAGE_OF_DICTIONARIES",
                    "Не удалось вывести страницу словарей");
        }

        return result;
    }

    public DictionaryModel getById(UUID id) {
        var result = dictionaryRepository.getById(id);
        if (result == null) {
            return new DictionaryModel("CAN_NOT_FIND_DICTIONARY",
                    "Не удалось найти словарь по введенному идентификатору");
        }

        return result;
    }

    public StringResultModel getTranslatedWord(DictionaryTranslateModel model) {
        var validationResult = stringValidation(model.getWord(), 100);
        if (validationResult.getErrorCode() != null) {
            return new StringResultModel("CAN_NOT_FIND_TRANSLATION",
                    "Не удалось найти данные. Поля должны быть корректно заполненными");
        }

        if (model.getFromLanguage() == null || model.getToLanguage() == null) {
            return new StringResultModel("CAN_NOT_FIND_TRANSLATION",
                    "Не удалось найти данные. Поля должны быть корректно заполненными");
        }

        return dictionaryRepository.getTranslatedWord(model);
    }

    public GUIDResultModel save(DictionarySaveModel model) {
        var validationResult = stringValidation(model.getWord(), 100);
        if (validationResult.getErrorCode() != null) {
            return new GUIDResultModel("CAN_NOT_SAVE_DICTIONARY",
                    "Не удалось сохранить данные. Поля должны быть заполненными");
        }

        validationResult = stringValidation(model.getTranslation(), 100);
        if (validationResult.getErrorCode() != null) {
            return new GUIDResultModel("CAN_NOT_SAVE_DICTIONARY",
                    "Не удалось сохранить данные. Поля должны быть заполненными");
        }

        if (model.getFromLanguage() == null || model.getToLanguage() == null) {
            return new GUIDResultModel("CAN_NOT_SAVE_DICTIONARY",
                    "Не удалось сохранить данные. Поля должны быть корректно заполненными");
        }

        var result = dictionaryRepository.save(model);
        if (result == null) {
            return new GUIDResultModel("CAN_NOT_SAVE_DICTIONARY",
                    "Не удалось сохранить данные. Поля должны быть заполненными");
        }

        return result;
    }

    public SuccessResultModel update(DictionaryUpdateModel model) {
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
            return new SuccessResultModel("CAN_NOT_UPDATE_DICTIONARY",
                    "Не удалось обновить данные. Поля должны быть корректно заполненными");
        }

        return new SuccessResultModel(true);
    }

    public SuccessResultModel delete(UUID id) {
        var deleteQuery =  dictionaryRepository.delete(id);
        if (deleteQuery == null) {
            return new SuccessResultModel("CAN_NOT_DELETE_DICTIONARY",
                    "Не удалось удалить словарь по введенному идентификатору");
        }

        return new SuccessResultModel(true);
    }
}
