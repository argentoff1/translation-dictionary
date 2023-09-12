package ru.mmtr.translationdictionary.domainservice.language;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.mmtr.translationdictionary.domain.common.*;
import ru.mmtr.translationdictionary.domain.language.LanguageModel;
import ru.mmtr.translationdictionary.domain.language.LanguagePageRequestModel;
import ru.mmtr.translationdictionary.domain.language.LanguageSaveModel;
import ru.mmtr.translationdictionary.domain.language.LanguageUpdateModel;
import ru.mmtr.translationdictionary.infrastructure.repositories.language.LanguageRepository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static ru.mmtr.translationdictionary.domainservice.common.Validation.stringValidation;

@Slf4j
@Service
public class LanguageService {
    private final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public CollectionResultModel<LanguageModel> showAll() {
        var result = languageRepository.showAll();
        if (result == null) {
            return new CollectionResultModel<>("CAN_NOT_SHOW_LANGUAGES",
                    "Не удалось вывести список всех языков");
        }

        return result;
    }

    public Map<UUID, LanguageModel> getByIds(List<UUID> idList) {
        return languageRepository.getByIds(idList);
    }

    public PageResultModel<LanguageModel> getPage(LanguagePageRequestModel criteria) {
        var result = languageRepository.getPage(criteria);
        if (result == null) {
            return new PageResultModel<>("CAN_NOT_RETURN_PAGE_OF_LANGUAGES",
                    "Не удалось вывести страницу языков");
        }

        return result;
    }

    public LanguageModel getById(UUID id) {
        var result = languageRepository.getById(id);
        if (result == null) {
            return new LanguageModel("CAN_NOT_FIND_LANGUAGE",
                    "Не удалось найти язык по введенному идентификатору");
        }

        return result;
    }

    public GUIDResultModel save(LanguageSaveModel model) {
        var validationResult = stringValidation(model.getLanguageName(), 15);
        if (validationResult.getErrorCode() != null) {
            return new GUIDResultModel("CAN_NOT_SAVE_LANGUAGE",
                    "Не удалось сохранить данные. Наименование языка должно быть корректно заполненным");
        }

        var result = languageRepository.save(model);
        if (result == null) {
            return new GUIDResultModel("CAN_NOT_SAVE_LANGUAGE",
                    "Не удалось сохранить язык");
        }

        return result;
    }

    public SuccessResultModel update(LanguageUpdateModel model) {
        var validationResult = stringValidation(model.getLanguageName(), 15);
        if (validationResult.getErrorCode() != null) {
            return validationResult;
        }

        var updateQuery = languageRepository.update(model);
        if (updateQuery == null) {
            return new SuccessResultModel("CAN_NOT_UPDATE_LANGUAGE",
                    "Не удалось обновить язык. Данные должны быть корректно заполнены");
        }

        return new SuccessResultModel(true);
    }

    public SuccessResultModel delete(UUID id) {
        var deleteQuery = languageRepository.delete(id);
        if (deleteQuery == null) {
            return new SuccessResultModel("CAN_NOT_DELETE_LANGUAGE",
                    "Не удалось удалить язык. Данные должны быть корректно заполнены");
        }

        return new SuccessResultModel(true);
    }
}
