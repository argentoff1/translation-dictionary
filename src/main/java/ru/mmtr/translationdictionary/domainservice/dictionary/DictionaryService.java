package ru.mmtr.translationdictionary.domainservice.dictionary;

import org.springframework.stereotype.Service;
import ru.mmtr.translationdictionary.domain.common.*;
import ru.mmtr.translationdictionary.domain.dictionary.*;
import ru.mmtr.translationdictionary.infrastructure.repositories.dictionary.DictionaryRepository;

import static ru.mmtr.translationdictionary.domainservice.common.Validation.stringValidation;

import java.util.List;
import java.util.Map;
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

    public Map<UUID, DictionaryModel> getByIds(List<UUID> idList) {

        return dictionaryRepository.getByIds(idList);
    }

    public CollectionResultModel<DictionaryWordAndTranslationModel> getAllByIds(DictionaryIdsCollectionModel<UUID> model) {

        return dictionaryRepository.getAllByIds(model);
    }

    public PageResultModel<DictionaryModel> getPage(DictionaryPageRequestModel criteria) {

        return dictionaryRepository.getPage(criteria);
    }

    public DictionaryModel getById(UUID id) {
        if (id == null) {
            return new DictionaryModel("CA_NOT_FIND",
                    "Невозможно найти словарь");
        }

        return dictionaryRepository.getById(id);
    }

    public StringResultModel getTranslatedWord(DictionaryTranslateModel model) {
        var validationResult = stringValidation(model.getWord(), 100);

        if (validationResult.getErrorCode() != null) {
            return new StringResultModel("CAN_NOT_FIND",
                    "Не удалось найти данные. Поля должны быть корректно заполненными");
        }

        var result = dictionaryRepository.getTranslatedWord(model);

        if (result == null) {
            return new StringResultModel("TRANSLATION_NOT_FOUND",
                    "Не удалось найти перевод данного слова");
        }

        return result;
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

        var result = dictionaryRepository.save(model);

        if (result == null) {
            return new GUIDResultModel("CAN_NOT_SAVE",
                    "Не удалось сохранить данные. Поля должны быть корректно заполненными");
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
            return new SuccessResultModel("CAN_NOT_UPDATE",
                    "Не удалось сохранить данные. Поля должны быть корректно заполненными");
        }

        return new SuccessResultModel(true);
    }

    public SuccessResultModel delete(UUID id) {
        var repositoryResult = dictionaryRepository.delete(id);

        if (repositoryResult == null) {
            return new SuccessResultModel("CAN_NOT_DELETE",
                    "Не удалось удалить данные. Поля должны быть корректно заполненными");
        }

        return new SuccessResultModel(true);
    }
}
