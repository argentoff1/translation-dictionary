package ru.mmtr.translationdictionary.domainservice.user;

import org.springframework.stereotype.Service;
import ru.mmtr.translationdictionary.domain.common.CollectionResultModel;
import ru.mmtr.translationdictionary.domain.common.GUIDResultModel;
import ru.mmtr.translationdictionary.domain.common.JwtResponseResultModel;
import ru.mmtr.translationdictionary.domain.common.SuccessResultModel;
import ru.mmtr.translationdictionary.domain.user.JwtRequestModel;
import ru.mmtr.translationdictionary.domain.user.UserModel;
import ru.mmtr.translationdictionary.domain.user.UserSaveModel;
import ru.mmtr.translationdictionary.domainservice.session.UserSessionService;
import ru.mmtr.translationdictionary.infrastructure.repositories.user.AdminRepository;

import java.util.UUID;

import static ru.mmtr.translationdictionary.domainservice.Validation.stringValidation;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final UserSessionService userSessionService;
    private final UserService userService;

    public AdminService(AdminRepository adminRepository, UserSessionService userSessionService, UserService userService) {
        this.adminRepository = adminRepository;
        this.userSessionService = userSessionService;
        this.userService = userService;
    }

    public JwtResponseResultModel login(JwtRequestModel model) {
        var validationResult = stringValidation(model.getLogin(), 20);
        if (validationResult.getErrorCode() != null) {
            return new JwtResponseResultModel("CAN_NOT_AUTHORIZE");
        }

        var findUser = userService.getByLogin(model.getLogin());
        if (findUser == null) {
            return new JwtResponseResultModel("CAN_NOT_AUTHORIZE");
        }

        userService.login(model);

        var session = userSessionService.saveAdmin(findUser);

        return new JwtResponseResultModel(session.getAccessToken(), session.getRefreshToken());
    }

    public CollectionResultModel<UserModel> showAllUsers() {
        return userService.showAllUsers();
    }

    public GUIDResultModel saveAdmin(UserSaveModel model) {
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

        var result = adminRepository.saveAdmin(model);

        if (result == null) {
            return new GUIDResultModel("CAN_NOT_SAVE",
                    "Не удалось сохранить данные. Поля должны быть корректно заполненными");
        }

        return result;
    }

    public SuccessResultModel archiveById(UUID id) {
        var repositoryResult = adminRepository.archiveById(id);

        if (repositoryResult.getErrorCode() != null) {
            return new SuccessResultModel("CAN_NOT_UPDATE",
                    "Не удалось сохранить данные. Поля должны быть корректно заполненными");
        }

        return repositoryResult;
    }

    public SuccessResultModel unarchiveById(UUID id) {
        var repositoryResult = adminRepository.unarchiveById(id);

        if (repositoryResult.getErrorCode() != null) {
            return new SuccessResultModel("CAN_NOT_UPDATE",
                    "Не удалось сохранить данные. Поля должны быть корректно заполненными");
        }

        return repositoryResult;
    }

    public SuccessResultModel logout(JwtRequestModel model) {
        return userService.logout(model);
    }
}
