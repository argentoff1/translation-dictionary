package ru.mmtr.translationdictionary.domainservice.user;

import io.jsonwebtoken.Claims;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import ru.mmtr.translationdictionary.domain.common.*;
import ru.mmtr.translationdictionary.domain.session.UserSessionModel;
import ru.mmtr.translationdictionary.domain.user.*;
import ru.mmtr.translationdictionary.domainservice.common.CommonUtils;
import ru.mmtr.translationdictionary.domainservice.common.Validation;
import ru.mmtr.translationdictionary.domainservice.session.UserSessionService;
import ru.mmtr.translationdictionary.infrastructure.repositories.user.UserRepository;
import ru.mmtr.translationdictionary.infrastructure.security.JwtProvider;

import java.util.List;
import java.util.Map;
import java.util.UUID;

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
        var validationResult = stringValidation(model.getLogin(), 20);
        if (validationResult.getErrorCode() != null) {
            return new JwtResponseResultModel("CAN_NOT_AUTHORIZE." +
                    " Некорректный логин");
        }

        var findUser = userRepository.getByLogin(model.getLogin());
        if (findUser == null) {
            return new JwtResponseResultModel("CAN_NOT_AUTHORIZE." +
                    " Некорректный логин");
        }

        if (!Validation.checkingForArchiving(findUser.getArchiveDate())) {
            return new JwtResponseResultModel("CAN_NOT_AUTHORIZE");
        }

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

    public JwtResponseResultModel refreshToken(String subject) {
        UserModel user = userRepository.getByLogin(subject);

        if (user == null) {
            return new JwtResponseResultModel("CAN_NOT_REFRESH_TOKEN. " +
                    "Неверный логин");
        }

        UserSessionModel session = userSessionService.getByUserId(user.getUserId());

        if (session == null) {
            return new JwtResponseResultModel("CA_NOT_REFRESH_TOKEN. " +
                    "Не удалось найти сессию");
        }

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

        return new JwtResponseResultModel("CAN_NOT_GENERATE_TOKEN. " +
                "Проверьте введенные данные еще раз");
    }

    public Map<UUID, UserModel> getByIds(List<UUID> idList) {
        return userRepository.getByIds(idList);
    }

    public CollectionResultModel<UserModel> showAllUsers() {
        var result = userRepository.showAllUsers();

        if (result == null) {
            return new CollectionResultModel<>("CAN_NOT_FIND_USERS",
                    "Не удалось найти список пользователей");
        }

        return result;
    }

    public PageResultModel<UserModel> getPageUsers(UserPageRequestModel criteria) {
        var result = userRepository.getPage(criteria);

        if (result == null) {
            return new PageResultModel<>("CAN_NOT_RETURN_PAGE",
                    "Не удалось вернуть страницу пользователей");
        }

        return result;
    }

    public UserModel getUserById(UUID id) {
        var result = userRepository.getById(id);

        if (result == null) {
            return new UserModel("CA_NOT_FIND_USER",
                    "Не удалось найти пользователя");
        }

        return result;
    }

    public UserModel getByLogin(String login) {
        var validationResult = stringValidation(login, 20);
        if (validationResult.getErrorCode() != null) {
            return new UserModel("CAN_NOT_FIND",
                    "Невозможно найти пользователя по указанному логину");
        }

        return userRepository.getByLogin(login);
    }

    public GUIDResultModel save(UserSaveModel model) {
        var validationResult = stringValidation(model.getLogin(), 20);
        if (validationResult.getErrorCode() != null) {
            return new GUIDResultModel("CAN_NOT_SAVE",
                    "Не удалось сохранить данные. Логин введен некорректно");
        }

        validationResult = stringValidation(model.getPassword(), 100);
        if (validationResult.getErrorCode() != null) {
            return new GUIDResultModel("CAN_NOT_SAVE",
                    "Не удалось сохранить данные. Пароль введен некорректно");
        }

        validationResult = stringValidation(model.getLastName(), 30);
        if (validationResult.getErrorCode() != null) {
            return new GUIDResultModel("CAN_NOT_SAVE",
                    "Не удалось сохранить данные. Фамилия введена некорректно");
        }

        validationResult = stringValidation(model.getFirstName(), 20);
        if (validationResult.getErrorCode() != null) {
            return new GUIDResultModel("CAN_NOT_SAVE",
                    "Не удалось сохранить данные. Имя введено некорректно");
        }

        validationResult = stringValidation(model.getFatherName(), 50);
        if (validationResult.getErrorCode() != null) {
            return new GUIDResultModel("CAN_NOT_SAVE",
                    "Не удалось сохранить данные. Отчество введено некорректно");
        }

        validationResult = stringValidation(model.getEmail(), 320);
        if (validationResult.getErrorCode() != null) {
            return new GUIDResultModel("CAN_NOT_SAVE",
                    "Не удалось сохранить данные. Электронная почта введена некорректно");
        }

        validationResult = stringValidation(model.getPhoneNumber(), 30);
        if (validationResult.getErrorCode() != null) {
            return new GUIDResultModel("CAN_NOT_SAVE",
                    "Не удалось сохранить данные. Номер телефона введен некорректно");
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
