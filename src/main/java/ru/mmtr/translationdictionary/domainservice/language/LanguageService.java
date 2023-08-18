package ru.mmtr.translationdictionary.domainservice.language;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.mmtr.translationdictionary.domain.common.CollectionResultModel;
import ru.mmtr.translationdictionary.domain.common.DateTimeResultModel;
import ru.mmtr.translationdictionary.domain.common.PageResultModel;
import ru.mmtr.translationdictionary.domain.common.SuccessResultModel;
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
    // +
    public CollectionResultModel<LanguageModel> showAll() {

        return languageRepository.showAll();
    }

    public PageResultModel<LanguageModel> getPage(LanguagePageRequestModel criteria) {


        return languageRepository.getPage(criteria);
    }
    // +
    public LanguageModel getById(UUID id) {
        if (id == null) {
            return null;
        }

        return languageRepository.getById(id);
    }

    public DateTimeResultModel getByName(String languageName) {
        stringValidation(languageName);

        var result = languageRepository.getByName(languageName);

        if (result == null) {
            return new DateTimeResultModel("LANGUAGE_NOT_FOUND", "Не удалось найти данные по введенному языку");
        }

        return new DateTimeResultModel(result.getCreatedAt(), result.getModifiedAt());
    }
    // +
    public SuccessResultModel save(LanguageSaveModel model) {
        stringValidation(model.getLanguageName());

        var result = languageRepository.save(model);

        if (result == null) {
            return new SuccessResultModel("CAN_NOT_SAVE", "Не удалось сохранить данные. Поля должны быть заполненными");
        }

        return result;
    }
    // +
    public SuccessResultModel update(LanguageUpdateModel model) {
        Integer repositoryResult = languageRepository.update(model);

        if (repositoryResult == null) {
            return new SuccessResultModel("CAN_NOT_UPDATE", "Не удалось сохранить данные. Поля должны быть заполненными");
        }

        return new SuccessResultModel(true);
    }

    public SuccessResultModel delete(UUID id) {
        Integer repositoryResult = languageRepository.delete(id);

        if (repositoryResult == null) {
            return new SuccessResultModel("CAN_NOT_DELETE", "Не удалось удалить данные. Поля должны быть заполненными");
        }

        return new SuccessResultModel(true);
    }

    private SuccessResultModel stringValidation(String str) {
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
