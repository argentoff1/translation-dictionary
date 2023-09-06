package ru.mmtr.translationdictionary.domainservice.user;

import io.jsonwebtoken.Claims;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import ru.mmtr.translationdictionary.domain.common.*;
import ru.mmtr.translationdictionary.domain.session.UserSessionModel;
import ru.mmtr.translationdictionary.domain.session.UserSessionPageRequestModel;
import ru.mmtr.translationdictionary.domain.user.*;
import ru.mmtr.translationdictionary.domainservice.common.CommonUtils;
import ru.mmtr.translationdictionary.domainservice.common.Validation;
import ru.mmtr.translationdictionary.domainservice.session.UserSessionService;
import ru.mmtr.translationdictionary.infrastructure.repositories.user.UserRepository;
import ru.mmtr.translationdictionary.infrastructure.security.JwtProvider;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static ru.mmtr.translationdictionary.domainservice.common.Validation.isValidUUID;
import static ru.mmtr.translationdictionary.domainservice.common.Validation.stringValidation;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserSessionService userSessionService;
    private final JwtProvider jwtProvider;

    public UserService(UserRepository userRepository, UserSessionService userSessionService, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.userSessionService = userSessionService;
        this.jwtProvider = jwtProvider;
    }

    public JwtResponseResultModel login(JwtRequestModel model) {
        // Проверка логина
        var validationResult = stringValidation(model.getLogin(), 20);
        if (validationResult.getErrorCode() != null) {
            return new JwtResponseResultModel("CAN_NOT_AUTHORIZE");
        }
        UserModel findUser = userRepository.getByLogin(model.getLogin());
        if (findUser == null) {
            return new JwtResponseResultModel("CAN_NOT_AUTHORIZE");
        }

        // Проверка на архивацию
        if (!Validation.checkingForArchiving(findUser.getArchiveDate())) {
            return new JwtResponseResultModel("CAN_NOT_AUTHORIZE");
        }

        // Проверка пароля
        validationResult = stringValidation(model.getPassword(), 100);
        if (validationResult.getErrorCode() != null) {
            return new JwtResponseResultModel("CAN_NOT_AUTHORIZE");
        }
        if (!BCrypt.checkpw(model.getPassword(), findUser.getPassword())) {
            return new JwtResponseResultModel("CAN_NOT_AUTHORIZE");
        }

        userRepository.login(model);

        UserSessionModel session = userSessionService.save(findUser);

        return new JwtResponseResultModel(session.getAccessToken(), session.getRefreshToken());
    }

    // Вот тут проблема
    public JwtResponseResultModel refreshToken(String subject) {
        UserModel user = userRepository.getByLogin(subject);

        UserSessionModel session = userSessionService.getByUserId(user.getUserId());

        if (jwtProvider.validateRefreshToken(session.getRefreshToken())) {
            final Claims claims = jwtProvider.getRefreshClaims(session.getRefreshToken());
            final String login = claims.getSubject();

            user = userRepository.getByLogin(login);

            final String newAccessToken = jwtProvider.generateAccessToken(user, CommonUtils.getSessionId());
            final String newRefreshToken = jwtProvider.generateRefreshToken(user, CommonUtils.getSessionId());

            var result = new JwtResponseResultModel(newAccessToken, newRefreshToken);

            userSessionService.updateTokens(user, result.getAccessToken(), result.getRefreshToken());

            return result;
        }

        return new JwtResponseResultModel("CAN_NOT_GENERATE_TOKEN");
    }

    public Map<UUID, UserModel> getByIds(List<UUID> idList) {
        return userRepository.getByIds(idList);
    }

    public JwtResponseResultModel getAccessToken(String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();

            final UserModel user = userRepository.getByLogin(login);
            final String accessToken = jwtProvider.generateAccessToken(user, CommonUtils.getSessionId());

            return new JwtResponseResultModel(accessToken, null);
        }

        return new JwtResponseResultModel("CAN_NOT_GENERATE_TOKEN");
    }

    public CollectionResultModel<UserModel> showAllUsers() {
        return userRepository.showAllUsers();
    }

    public CollectionResultModel<UserSessionModel> showAllSessions() {
        return userSessionService.showAll();
    }

    public PageResultModel<UserModel> getPageUsers(UserPageRequestModel criteria) {
        return userRepository.getPage(criteria);
    }

    public PageResultModel<UserSessionModel> getPageSessions(UserSessionPageRequestModel criteria) {
        return userSessionService.getPage(criteria);
    }

    public UserModel getUserById(UUID id) {
        if (!isValidUUID(String.valueOf(id))) {
            return new UserModel("CAN_NOT_FIND",
                    "Не удалось найти данные. Поля должны быть корректно заполнены");
        }

        return userRepository.getById(id);
    }

    public UserSessionModel getSessionById(UUID id) {
        if (!isValidUUID(String.valueOf(id))) {
            return new UserSessionModel("CAN_NOT_FIND",
                    "Не удалось найти данные. Поля должны быть корректно заполнены");
        }

        return userSessionService.getBySessionId(id);
    }

    public UserModel getByLogin(String login) {
        if (login == null) {
            return new UserModel("CA_NOT_FIND",
                    "Невозможно найти пользователя по указанному логину");
        }

        return userRepository.getByLogin(login);
    }

    public GUIDResultModel save(UserSaveModel model) {
        var validationResult = stringValidation(model.getLogin(), 20);

        if (validationResult.getErrorCode() != null) {
            return new GUIDResultModel("CAN_NOT_SAVE",
                    "Не удалось сохранить данные. Поля должны быть корректно заполненными");
        }

        validationResult = stringValidation(model.getPassword(), 100);

        if (validationResult.getErrorCode() != null) {
            return new GUIDResultModel("CAN_NOT_SAVE",
                    "Не удалось сохранить данные. Поля должны быть заполненными");
        }

        validationResult = stringValidation(model.getLastName(), 30);

        if (validationResult.getErrorCode() != null) {
            return new GUIDResultModel("CAN_NOT_SAVE",
                    "Не удалось сохранить данные. " +
                            "Поля должны быть корректно заполненными");
        }

        validationResult = stringValidation(model.getFirstName(), 20);

        if (validationResult.getErrorCode() != null) {
            return new GUIDResultModel("CAN_NOT_SAVE",
                    "Не удалось сохранить данные. " +
                            "Поля должны быть корректно заполненными");
        }

        validationResult = stringValidation(model.getFatherName(), 50);

        if (validationResult.getErrorCode() != null) {
            return new GUIDResultModel("CAN_NOT_SAVE",
                    "Не удалось сохранить данные. " +
                            "Поля должны быть корректно заполненными");
        }

        validationResult = stringValidation(model.getEmail(), 320);

        if (validationResult.getErrorCode() != null) {
            return new GUIDResultModel("CAN_NOT_SAVE",
                    "Не удалось сохранить данные. " +
                            "Поля должны быть корректно заполненными");
        }

        validationResult = stringValidation(model.getPhoneNumber(), 30);

        if (validationResult.getErrorCode() != null) {
            return new GUIDResultModel("CAN_NOT_SAVE",
                    "Не удалось сохранить данные. " +
                            "Поля должны быть корректно заполненными");
        }

        var result = userRepository.save(model);

        if (result == null) {
            return new GUIDResultModel("CAN_NOT_SAVE",
                    "Не удалось сохранить данные. Поля должны быть корректно заполненными");
        }

        return result;
    }

    public SuccessResultModel updateUser(UserUpdateModel model) {
        var validationResult = stringValidation(model.getLogin(), 20);

        if (validationResult.getErrorCode() != null) {
            return validationResult;
        }

        validationResult = stringValidation(model.getLastName(), 30);

        if (validationResult.getErrorCode() != null) {
            return validationResult;
        }

        validationResult = stringValidation(model.getFirstName(), 20);

        if (validationResult.getErrorCode() != null) {
            return validationResult;
        }

        validationResult = stringValidation(model.getFatherName(), 50);

        if (validationResult.getErrorCode() != null) {
            return validationResult;
        }

        validationResult = stringValidation(model.getEmail(), 320);

        if (validationResult.getErrorCode() != null) {
            return validationResult;
        }

        validationResult = stringValidation(model.getPhoneNumber(), 30);

        if (validationResult.getErrorCode() != null) {
            return validationResult;
        }

        var result = userRepository.updateUser(model);

        if (result == null) {
            return new SuccessResultModel("CAN_NOT_SAVE",
                    "Не удалось сохранить данные. " +
                            "Поля должны быть корректно заполненными");
        }

        return new SuccessResultModel(true);
    }

    public SuccessResultModel updateLogin(UserLoginUpdateModel model) {
        var validationResult = stringValidation(model.getLogin(), 20);

        if (validationResult.getErrorCode() != null) {
            return validationResult;
        }

        var result = userRepository.updateLogin(model);

        if (result == null) {
            return new SuccessResultModel("CAN_NOT_SAVE",
                    "Не удалось сохранить данные. Поля должны быть корректно заполненными");
        }

        return new SuccessResultModel(true);
    }

    public SuccessResultModel updatePassword(UserPasswordUpdateModel model) {
        var validationResult = stringValidation(model.getPassword(), 100);

        if (validationResult.getErrorCode() != null) {
            return validationResult;
        }

        var result = userRepository.updatePassword(model);

        if (result == null) {
            return new SuccessResultModel("CAN_NOT_SAVE",
                    "Не удалось сохранить данные. Поля должны быть корректно заполненными");
        }

        return new SuccessResultModel(true);
    }

    public SuccessResultModel archiveById(UUID id) {
        var repositoryResult = userRepository.archiveById(id);

        if (repositoryResult.getErrorCode() != null) {
            return new SuccessResultModel("CAN_NOT_UPDATE",
                    "Не удалось сохранить данные. Поля должны быть корректно заполненными");
        }

        return repositoryResult;
    }

    public SuccessResultModel unarchiveById(UUID id) {
        var repositoryResult = userRepository.unarchiveById(id);

        if (repositoryResult.getErrorCode() != null) {
            return new SuccessResultModel("CAN_NOT_UPDATE",
                    "Не удалось сохранить данные. Поля должны быть корректно заполненными");
        }

        return repositoryResult;
    }

    public SuccessResultModel logout() {
        var findUser = userRepository.getByLogin(CommonUtils.getSubject());

        var validationResult = Validation.stringValidation(findUser.getLogin(), 20);
        if (validationResult.getErrorCode() != null) {
            return new SuccessResultModel("CAN_NOT_LOGOUT",
                    "Не удалось выйти из системы. Поля должны быть корректно заполненными");
        }

        validationResult = stringValidation(findUser.getPassword(), 100);
        if (validationResult.getErrorCode() != null) {
            return new SuccessResultModel("CAN_NOT_LOGOUT", "Не удалось выйти из системы");
        }

        userSessionService.delete(findUser);

        return userRepository.logout();
    }
}
