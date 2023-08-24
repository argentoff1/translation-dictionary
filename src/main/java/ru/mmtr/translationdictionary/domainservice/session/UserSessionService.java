package ru.mmtr.translationdictionary.domainservice.session;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.mmtr.translationdictionary.domain.common.CollectionResultModel;
import ru.mmtr.translationdictionary.domain.common.GUIDResultModel;
import ru.mmtr.translationdictionary.domain.common.PageResultModel;
import ru.mmtr.translationdictionary.domain.common.SuccessResultModel;
import ru.mmtr.translationdictionary.domain.session.UserSessionModel;
import ru.mmtr.translationdictionary.domain.session.UserSessionPageRequestModel;
import ru.mmtr.translationdictionary.domain.session.UserSessionSaveModel;
import ru.mmtr.translationdictionary.domain.user.UserModel;
import ru.mmtr.translationdictionary.infrastructure.repositories.session.UserSessionRepository;

import java.util.UUID;

@Service
public class UserSessionService {
    private final UserSessionRepository userSessionRepository;

    public UserSessionService(UserSessionRepository userSessionRepository) {
        this.userSessionRepository = userSessionRepository;
    }

    public CollectionResultModel<UserSessionModel> showAll() {

        return userSessionRepository.showAll();
    }

    public PageResultModel<UserSessionModel> getPage(UserSessionPageRequestModel criteria) {

        return userSessionRepository.getPage(criteria);
    }

    public UserSessionModel getById(UUID id) {
        if (id == null) {
            return null;
        }

        return userSessionRepository.getById(id);
    }

    public UserSessionModel save(UserModel model) {
        var result = userSessionRepository.save(model);

        if (result == null) {
            return new UserSessionModel("CAN_NOT_SAVE",
                    "Не удалось сохранить данные. Поля должны быть корректно заполненными");
        }

        return result;
    }

    public SuccessResultModel delete(UUID id) {
        var repositoryResult = userSessionRepository.delete(id);

        if (repositoryResult == null) {
            return new SuccessResultModel("CAN_NOT_DELETE",
                    "Не удалось удалить данные. Поля должны быть корректно заполненными");
        }

        return new SuccessResultModel(true);
    }

    private SuccessResultModel stringValidation(String str, int countChars) {
        if (StringUtils.isBlank(str)) {
            return new SuccessResultModel("FIELD_MUST_BE_FILLED",
                    "Поле должно быть заполнено");
        }

        if (str.contains(" ")) {
            return new SuccessResultModel("FIELD_MUST_NOT_CONTAIN_SPACES",
                    "Поле не должно содержать пробелов");
        }

        int count = 0;
        for (char element : str.toCharArray()) {
            count++;
        }
        if (count > 15) {
            return new SuccessResultModel("FIELD_VALUE_OUT_OF_BOUNDS",
                    "Поле не должно быть больше 15 символов");
        }

        return new SuccessResultModel(true);
    }
}
