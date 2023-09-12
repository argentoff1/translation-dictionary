package ru.mmtr.translationdictionary.domainservice.session;

import org.springframework.stereotype.Service;
import ru.mmtr.translationdictionary.domain.common.PageResultModel;
import ru.mmtr.translationdictionary.domain.common.SuccessResultModel;
import ru.mmtr.translationdictionary.domain.session.UserSessionModel;
import ru.mmtr.translationdictionary.domain.session.UserSessionPageRequestModel;
import ru.mmtr.translationdictionary.domain.user.UserLoginLogoutModel;
import ru.mmtr.translationdictionary.infrastructure.repositories.session.UserSessionRepository;

import java.util.UUID;

@Service
public class UserSessionService {
    private final UserSessionRepository userSessionRepository;

    public UserSessionService(UserSessionRepository userSessionRepository) {
        this.userSessionRepository = userSessionRepository;
    }

    public PageResultModel<UserSessionModel> getPage(UserSessionPageRequestModel criteria) {
        var result = userSessionRepository.getPageSessions(criteria);

        if (result == null) {
            return new PageResultModel<>("CAN_NOT_FIND_SESSIONS",
                    "Не удалось найти список сессий");
        }

        return result;
    }

    public UserSessionModel getByUserId(UUID id) {
        var result = userSessionRepository.getByUserId(id);

        if (result == null) {
            return new UserSessionModel("CAN_NOT_FIND_SESSION",
                    "Не удалось найти сессию по введенному идентификатору");
        }

        return result;
    }

    public UserSessionModel save(UserLoginLogoutModel model) {
        var result = userSessionRepository.save(model);

        if (result == null) {
            return new UserSessionModel("CAN_NOT_SAVE",
                    "Не удалось сохранить данные. Поля должны быть корректно заполненными");
        }

        return result;
    }

    public void updateTokens(UserLoginLogoutModel user, String accessToken, String refreshToken) {
        var result = userSessionRepository.updateTokens(user, accessToken, refreshToken);

        if (result == null) {
            new SuccessResultModel("CAN_NOT_UPDATE",
                    "Не удалось обновить токены");
            return;
        }

        new SuccessResultModel(true);
    }

    public SuccessResultModel delete(UserLoginLogoutModel model) {
        var result = userSessionRepository.delete(model);

        if (result.getErrorCode() != null) {
            return new SuccessResultModel("CAN_NOT_DELETE",
                    "Не удалось удалить данные. Поля должны быть корректно заполненными");
        }

        return new SuccessResultModel(true);
    }
}
