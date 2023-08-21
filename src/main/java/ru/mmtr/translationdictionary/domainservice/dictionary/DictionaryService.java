package ru.mmtr.translationdictionary.domainservice.dictionary;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.mmtr.translationdictionary.domain.common.*;
import ru.mmtr.translationdictionary.domain.dictionary.*;
import ru.mmtr.translationdictionary.infrastructure.repositories.dictionary.DictionaryRepository;

import java.util.Objects;
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

    public CollectionResultModel<DictionaryWordAndTranslationModel> getAllByIds(DictionaryIdsCollectionModel<UUID> model) {

        return dictionaryRepository.getAllByIds(model);
    }

    public PageResultModel<DictionaryModel> getPage(DictionaryPageRequestModel criteria) {

        return dictionaryRepository.getPage(criteria);
    }

    public DictionaryModel getById(UUID id) {
        if (id == null) {
            return null;
        }

        return dictionaryRepository.getById(id);
    }

    public StringResultModel getTranslatedWord(DictionaryTranslateModel model) {
        var result = dictionaryRepository.getTranslatedWord(model);

        if (result == null) {
            return new StringResultModel("TRANSLATION_NOT_FOUND", "Не удалось найти перевод данного слова");
        }

        return result;
    }

    public GUIDResultModel save(DictionarySaveModel model) {
        var validationResult = stringValidation(model.getWord(), 100);

        if (validationResult.getErrorCode() != null) {
            return (GUIDResultModel) validationResult;
        }

        validationResult = stringValidation(model.getTranslation(), 100);

        if (validationResult.getErrorCode() != null) {
            return (GUIDResultModel) validationResult;
        }

        var result = dictionaryRepository.save(model);

        if (result == null) {
            return new GUIDResultModel("CAN_NOT_SAVE", "Не удалось сохранить данные. Поля должны быть корректно заполненными");
        }

        return result;
    }

    public SuccessResultModel update(DictionaryUpdateModel model) {
        var validationResult = stringValidation(model.getWord(), 100);

        if (validationResult.getErrorCode() != null) {
            return (SuccessResultModel) validationResult;
        }

        validationResult = stringValidation(model.getTranslation(), 100);

        if (validationResult.getErrorCode() != null) {
            return (SuccessResultModel) validationResult;
        }

        Integer repositoryResult = dictionaryRepository.update(model);

        if (repositoryResult == null) {
            return new SuccessResultModel("CAN_NOT_UPDATE",
                    "Не удалось сохранить данные. Поля должны быть корректно заполненными");
        }

        return new SuccessResultModel(true);
    }

    public SuccessResultModel delete(UUID id) {
        Integer repositoryResult = dictionaryRepository.delete(id);

        if (repositoryResult == null) {
            return new SuccessResultModel("CAN_NOT_DELETE", "Не удалось удалить данные. Поля должны быть корректно заполненными");
        }

        return new SuccessResultModel(true);
    }

    private GeneralResultModel stringValidation(String str, int countChars) {
        if (StringUtils.isNotBlank(str)) {
            return new SuccessResultModel("FIELD_MUST_BE_FILLED", "Поле должно быть заполнено");
        }

        if (str.contains(" ")) {
            return new SuccessResultModel("FIELD_MUST_NOT_CONTAIN_SPACES", "Поле не должно содержать пробелов");
        }

        int count = 0;
        for (char element : str.toCharArray()) {
            count++;
        }
        if (count > 15) {
            return new SuccessResultModel("FIELD_VALUE_OUT_OF_BOUNDS", "Поле не должно быть больше 15 символов");
        }

        return new SuccessResultModel(true);
    }
}
