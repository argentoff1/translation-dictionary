package ru.mmtr.translationdictionary.domainservice.user;

//import org.springframework.security.crypto.password.PasswordEncoder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.mmtr.translationdictionary.domain.common.GUIDResultModel;
import ru.mmtr.translationdictionary.domain.common.SuccessResultModel;
import ru.mmtr.translationdictionary.domain.user.*;
import ru.mmtr.translationdictionary.infrastructure.repositories.user.UserRepository;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    //private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository/*, PasswordEncoder passwordEncoder*/) {
        this.userRepository = userRepository;
        //this.passwordEncoder = passwordEncoder;
    }

    public GUIDResultModel login(UserAuthorizationModel model) {
        var result = userRepository.login(model);

        if (result == null) {
            return new GUIDResultModel("CAN_NOT_AUTHORIZE",
                    "Не удалось авторизоваться. " +
                            "Поля должны быть корректно заполнены");
        }

        if (result.getResultId() == null) {
            return new GUIDResultModel("CAN_NOT_AUTHORIZE",
                    "Не удалось авторизоваться. " +
                            "Поля должны быть корректно заполнены");
        }

        return result;
    }

    public SuccessResultModel save(UserSaveModel model) {
        var validationResult = stringValidation(model.getLogin());

        if (validationResult.getErrorCode() != null) {
            return validationResult;
        }

        validationResult = stringValidation(model.getPassword());

        if (validationResult.getErrorCode() != null) {
            return validationResult;
        }

        validationResult = stringValidation(model.getLastName());

        if (validationResult.getErrorCode() != null) {
            return validationResult;
        }

        validationResult = stringValidation(model.getFirstName());

        if (validationResult.getErrorCode() != null) {
            return validationResult;
        }

        validationResult = stringValidation(model.getFatherName());

        if (validationResult.getErrorCode() != null) {
            return validationResult;
        }

        validationResult = stringValidation(model.getEmail());

        if (validationResult.getErrorCode() != null) {
            return validationResult;
        }

        validationResult = stringValidation(model.getPhoneNumber());

        if (validationResult.getErrorCode() != null) {
            return validationResult;
        }

        var result = userRepository.save(model);

        if (result == null) {
            return new SuccessResultModel("CAN_NOT_SAVE",
                    "Не удалось сохранить данные. Поля должны быть корректно заполненными");
        }

        //String hashedPassword = passwordEncoder.encode(model.getPassword());

        return result;
    }

    public SuccessResultModel updateUser(UserUpdateModel model) {
        var validationResult = stringValidation(model.getLogin());

        if (validationResult.getErrorCode() != null) {
            return validationResult;
        }

        validationResult = stringValidation(model.getLastName());

        if (validationResult.getErrorCode() != null) {
            return validationResult;
        }

        validationResult = stringValidation(model.getFirstName());

        if (validationResult.getErrorCode() != null) {
            return validationResult;
        }

        validationResult = stringValidation(model.getFatherName());

        if (validationResult.getErrorCode() != null) {
            return validationResult;
        }

        validationResult = stringValidation(model.getEmail());

        if (validationResult.getErrorCode() != null) {
            return validationResult;
        }

        validationResult = stringValidation(model.getPhoneNumber());

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

    public SuccessResultModel updatePassword(UserPasswordUpdateModel model) {
        var validationResult = stringValidation(model.getPassword());

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
        Integer repositoryResult = userRepository.archiveById(id);

        if (repositoryResult == null) {
            return new SuccessResultModel("CAN_NOT_UPDATE",
                    "Не удалось сохранить данные. Поля должны быть корректно заполненными");
        }

        return new SuccessResultModel(true);
    }

    public SuccessResultModel unarchiveById(UUID id) {
        Integer repositoryResult = userRepository.unarchiveById(id);

        if (repositoryResult == null) {
            return new SuccessResultModel("CAN_NOT_UPDATE",
                    "Не удалось сохранить данные. Поля должны быть корректно заполненными");
        }

        return new SuccessResultModel(true);
    }

    public SuccessResultModel logout() {

        return userRepository.logout();
    }

    private SuccessResultModel stringValidation(String str) {
        if (StringUtils.isNotBlank(str)) {
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
        if (count > 50) {
            return new SuccessResultModel("FIELD_VALUE_OUT_OF_BOUNDS",
                    "Поле не должно быть больше 15 символов");
        }

        return new SuccessResultModel(true);
    }
}
