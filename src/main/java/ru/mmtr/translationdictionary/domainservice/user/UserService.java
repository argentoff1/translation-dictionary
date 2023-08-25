package ru.mmtr.translationdictionary.domainservice.user;

import org.springframework.stereotype.Service;
import ru.mmtr.translationdictionary.domain.common.*;
import ru.mmtr.translationdictionary.domain.user.*;
import ru.mmtr.translationdictionary.domainservice.Validation;
import ru.mmtr.translationdictionary.domainservice.session.UserSessionService;
import ru.mmtr.translationdictionary.infrastructure.repositories.user.UserRepository;

import java.util.UUID;

import static ru.mmtr.translationdictionary.domainservice.Validation.stringValidation;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserSessionService userSessionService;

    public UserService(UserRepository userRepository, UserSessionService userSessionService) {
        this.userRepository = userRepository;
        this.userSessionService = userSessionService;
    }

    public JwtResponseResultModel login(JwtRequestModel model) {
        var validationResult = stringValidation(model.getLogin(), 20);
        if (validationResult.getErrorCode() != null) {
            return new JwtResponseResultModel("CAN_NOT_AUTHORIZE");
        }

        var findUser = userRepository.getByLogin(model.getLogin());
        if (findUser == null) {
            return new JwtResponseResultModel("CAN_NOT_AUTHORIZE");
        }

        userRepository.login(model);

        var session = userSessionService.save(findUser);

        return new JwtResponseResultModel(session.getAccessToken(), session.getRefreshToken());
    }

    public UserModel getById(UUID id) {
        if (id == null) {
            return new UserModel("CAN_NOT_FIND",
                    "Не удалось найти данные. Поля должны быть корректно заполненными");
        }

        return userRepository.getById(id);
    }

    public UserModel getByLogin(String login) {
        if (login == null) {
            return null;
        }

        return userRepository.getByLogin(login);
    }

    public PageResultModel<UserModel> getPage(UserPageRequestModel criteria) {

        return userRepository.getPage(criteria);
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

        Integer result = userRepository.updateUser(model);

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

        Integer result = userRepository.updateLogin(model);

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

        Integer result = userRepository.updatePassword(model);

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

    public SuccessResultModel logout(JwtRequestModel model) {
        var validationResult = Validation.stringValidation(model.getLogin(), 20);
        if (validationResult.getErrorCode() != null) {
            return new SuccessResultModel("CAN_NOT_LOGOUT",
                    "Не удалось сохранить данные. Поля должны быть корректно заполненными");
        }

        var findUser = userRepository.getByLogin(model.getLogin());
        if (findUser == null) {
            return new SuccessResultModel("CAN_NOT_LOGOUT",
                    "Не удалось сохранить данные. Поля должны быть корректно заполненными");
        }

        userSessionService.delete(findUser);

        return userRepository.logout();
    }
}
