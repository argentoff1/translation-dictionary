package ru.mmtr.translationdictionary.domainservice.dictionary;

import org.springframework.stereotype.Service;
import ru.mmtr.translationdictionary.domain.common.PageModel;
import ru.mmtr.translationdictionary.domain.common.PageResultModel;
import ru.mmtr.translationdictionary.domain.common.SuccessResultModel;
import ru.mmtr.translationdictionary.domain.dictionary.DictionaryModel;
import ru.mmtr.translationdictionary.infrastructure.repositories.dictionary.DictionaryRepository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class DictionaryService {
    private final DictionaryRepository dictionaryRepository;

    public DictionaryService(DictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }

    public List<DictionaryModel> showAll() {

        return dictionaryRepository.showAll();
    }

    public PageResultModel<DictionaryModel> showAllWithPagination(PageModel model) {

        return dictionaryRepository.showAllWithPagination(model);
    }

    public DictionaryModel getById(UUID id) {

        return dictionaryRepository.getById(id);
    }

    public DictionaryModel getTranslatedWord(String word, UUID fromLanguage, UUID toLanguage) {
        var result = dictionaryRepository.getTranslatedWord(word, fromLanguage, toLanguage);

        if (Objects.isNull(result)) {
            new SuccessResultModel("TRANSLATION_NOT_FOUND", "Не удалось найти перевод данного слова");
        }

        return result;
    }

    public SuccessResultModel save(String word, String translation, UUID fromLanguage, UUID toLanguage) {
        checkIsEmpty(word);
        checkForSpaces(word);
        if (countChars(word) > 15) {
            return new SuccessResultModel("FIELD_VALUE_OUT_OF_BOUNDS", "Поле не должно быть больше 15 символов");
        }

        checkIsEmpty(translation);
        checkForSpaces(translation);
        if (countChars(translation) > 15) {
            return new SuccessResultModel("FIELD_VALUE_OUT_OF_BOUNDS", "Поле не должно быть больше 15 символов");
        }

        var result = dictionaryRepository.save(word, translation, fromLanguage, toLanguage);

        if (Objects.isNull(result)) {
            new SuccessResultModel("CAN_NOT_SAVE", "Не удалось сохранить пару слов");
        }

        return result;
    }

    public SuccessResultModel update(UUID id, String word, String translation) {
        var result = dictionaryRepository.update(id, word, translation);

        if (Objects.isNull(result)) {
            new SuccessResultModel("FIELD_MUST_BE_FILLED", "Поле должно быть заполнено");
        }

        return result;
    }

    public SuccessResultModel delete(UUID id) {

        return dictionaryRepository.delete(id);
    }

    public static void checkIsEmpty(String str) {
        if (str.isEmpty()) {
            new SuccessResultModel("FIELD_MUST_BE_FILLED", "Поле должно быть заполнено");
            return;
        }
        new SuccessResultModel(true);
    }

    public static void checkForSpaces(String str) {
        if (str.contains(" ")) {
            new SuccessResultModel("FIELD_MUST_NOT_CONTAIN_SPACES", "Поле не должно содержать пробелов");
            return;
        }
        new SuccessResultModel(true);
    }

    public static int countChars(String str) {
        int count = 0;
        for (char element : str.toCharArray()) {
            count++;
        }
        return count;
    }
}
