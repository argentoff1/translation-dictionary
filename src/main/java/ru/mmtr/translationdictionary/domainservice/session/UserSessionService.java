package ru.mmtr.translationdictionary.domainservice.session;

import org.springframework.stereotype.Service;
import ru.mmtr.translationdictionary.domain.common.CollectionResultModel;
import ru.mmtr.translationdictionary.domain.common.JwtResponseResultModel;
import ru.mmtr.translationdictionary.domain.common.PageResultModel;
import ru.mmtr.translationdictionary.domain.common.SuccessResultModel;
import ru.mmtr.translationdictionary.domain.session.UserSessionModel;
import ru.mmtr.translationdictionary.domain.session.UserSessionPageRequestModel;
import ru.mmtr.translationdictionary.domain.user.UserModel;
import ru.mmtr.translationdictionary.domainservice.common.CommonUtils;
import ru.mmtr.translationdictionary.domainservice.user.UserService;
import ru.mmtr.translationdictionary.infrastructure.repositories.session.UserSessionRepository;

import java.util.UUID;

import static ru.mmtr.translationdictionary.domainservice.common.Validation.isValidUUID;

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

    /*public JwtResponseResultModel getRefreshToken(String subject) {
        var result = userSessionRepository.getRefreshToken(subject);
        if (result == null) {
            return new JwtResponseResultModel("CAN_NOT_FIND_REFRESH_TOKEN");
        }

        UserModel foundUser = userService.getUserById(CommonUtils.getUserId());

        var tokens = userSessionRepository.save(foundUser);

        return new JwtResponseResultModel(tokens.getAccessToken(), tokens.getRefreshToken());

        //return result;
    }*/

    public UserSessionModel getById(UUID id) {
        if (!isValidUUID(String.valueOf(id))) {
            return new UserSessionModel("CAN_NOT_FIND",
                    "Не удалось найти данные. Поля должны быть корректно заполнены");
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

    public SuccessResultModel updateTokens(UserModel user, String accessToken, String refreshToken) {
        var result = userSessionRepository.updateTokens(user, accessToken, refreshToken);

        if (result == null) {
            return new SuccessResultModel("CAN_NOT_UPDATE",
                    "Не удалось обновить токены");
        }

        return new SuccessResultModel(true);
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
