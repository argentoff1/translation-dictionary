package ru.mmtr.translationdictionary.domainservice.dictionary;

import org.springframework.stereotype.Service;
import ru.mmtr.translationdictionary.domain.common.*;
import ru.mmtr.translationdictionary.domain.dictionary.DictionaryModel;
import ru.mmtr.translationdictionary.domain.dictionary.DictionaryPageRequestModel;
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

    public PageResultModel<DictionaryModel> getPage(DictionaryPageRequestModel criteria) {

        return dictionaryRepository.getPage(criteria);
    }

    public DictionaryModel getById(UUID id) {
        if (id == null) {
            return null;
        }

        return dictionaryRepository.getById(id);
    }

    public StringResultModel getTranslatedWord(String word, UUID fromLanguage, UUID toLanguage) {
        var result = dictionaryRepository.getTranslatedWord(word, fromLanguage, toLanguage);

        if (Objects.isNull(result)) {
            return new StringResultModel("TRANSLATION_NOT_FOUND", "Не удалось найти перевод данного слова");
        }

        return result;
    }

    public SuccessResultModel save(String word, String translation, UUID fromLanguage, UUID toLanguage) {
        stringValidation(word);

        stringValidation(translation);

        var result = dictionaryRepository.save(word, translation, fromLanguage, toLanguage);

        if (Objects.isNull(result)) {
            return new SuccessResultModel("CAN_NOT_SAVE", "Не удалось сохранить данные. Поля должны быть заполненными");
        }

        return result;
    }

    public SuccessResultModel update(UUID id, String word, String translation) {
        Integer repositoryResult = dictionaryRepository.update(id, word, translation);

        if (Objects.isNull(repositoryResult)) {
            return new SuccessResultModel("CAN_NOT_SAVE", "Не удалось сохранить данные. Поля должны быть заполненными");
        }

        return new SuccessResultModel(true);
    }

    public SuccessResultModel delete(UUID id) {
        Integer repositoryResult = dictionaryRepository.delete(id);

        if (Objects.isNull(repositoryResult)){
            return new SuccessResultModel("CAN_NOT_DELETE", "Не удалось удалить данные. Поля должны быть заполненными");
        }

        return new SuccessResultModel(true);
    }

    public SuccessResultModel stringValidation(String str) {
        if (str.isEmpty()) {
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
