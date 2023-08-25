package ru.mmtr.translationdictionary.domainservice.session;

import org.springframework.stereotype.Service;
import ru.mmtr.translationdictionary.domain.common.CollectionResultModel;
import ru.mmtr.translationdictionary.domain.common.PageResultModel;
import ru.mmtr.translationdictionary.domain.common.SuccessResultModel;
import ru.mmtr.translationdictionary.domain.session.UserSessionModel;
import ru.mmtr.translationdictionary.domain.session.UserSessionPageRequestModel;
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
        return userSessionRepository.getPageSessions(criteria);
    }

    public UserSessionModel getById(UUID id) {
        if (id == null) {
            return null;
        }

        return userSessionRepository.getById(id);
    }

    public UserSessionModel saveAdmin(UserModel model) {
        var result = userSessionRepository.saveAdmin(model);

        if (result == null) {
            return new UserSessionModel("CAN_NOT_SAVE",
                    "Не удалось сохранить данные. Поля должны быть корректно заполненными");
        }

        return result;
    }

    public UserSessionModel saveUser(UserModel model) {
        var result = userSessionRepository.saveUser(model);

        if (result == null) {
            return new UserSessionModel("CAN_NOT_SAVE",
                    "Не удалось сохранить данные. Поля должны быть корректно заполненными");
        }

        return result;
    }

    public SuccessResultModel delete(UserModel model) {
        var result = userSessionRepository.delete(model);

        if (result == null) {
            return new SuccessResultModel("CAN_NOT_DELETE",
                    "Не удалось удалить данные. Поля должны быть корректно заполненными");
        }

        return new SuccessResultModel(true);
    }
}
